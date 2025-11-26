package com.codepunk.moviepunk.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import com.codepunk.moviepunk.BuildConfig
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.dao.CuratedContentDao
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.local.dao.MovieDao
import com.codepunk.moviepunk.data.local.entity.GenreEntity
import com.codepunk.moviepunk.data.mapper.*
import com.codepunk.moviepunk.data.paging.TrendingMoviePagerFactory
import com.codepunk.moviepunk.data.remote.util.WebScraper
import com.codepunk.moviepunk.data.remote.util.toApiEither
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.domain.model.*
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant

class MoviePunkRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val curatedContentDao: CuratedContentDao,
    private val db: MoviePunkDatabase,
    private val genreDao: GenreDao,
    private val movieDao: MovieDao,
    private val webservice: MoviePunkWebservice,
    private val trendingMoviePagerFactory: TrendingMoviePagerFactory,
    private val webScraper: WebScraper
) : MoviePunkRepository {

    // region Methods

    private suspend fun getNewestGenre(): Instant = try {
        genreDao.getNewestGenre()
    } catch (_: Exception) {
        Instant.DISTANT_PAST
    }

    /**
     * This version uses [channelFlow] to emit cached data immediately
     * and then update it if necessary. It relies on [GenreDao.getGenres] returning a
     * [Flow]<[List]<[GenreEntity]>>.
     */
    private fun getLocalGenres(): Flow<Either<Exception, List<GenreEntity>>> = channelFlow {
        // Emit cached genres (and keep emitting as they are updated)
        launch {
            genreDao.getGenres()
                .map { localGenres -> localGenres.right() }
                .catch { e -> e.left() }
                .collect { send(it) }
        }

        // Check if cached genres need to be updated
        val duration = BuildConfig.DATA_REFRESH_DURATION_MINUTES.minutes
        val needsRefresh = Clock.System.now() - getNewestGenre() > duration
        if (needsRefresh) {
            Timber.i(message = "Genre data is out of date, updating")
            either {
                // TODO Do I need all of these separate try's? Can they be combined?
                // bind() will raise any non-CancellationException from toApiEither()
                val movieGenreDtos = try {
                    webservice.fetchGenres(EntityType.MOVIE).toApiEither().bind().genres
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    raise(e)
                }
                val tvGenreDtos = try {
                    webservice.fetchGenres(EntityType.TV).toApiEither().bind().genres
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    raise(e)
                }

                // Catch any exceptions while respecting cancellation
                val genreEntities = try {
                    combineToGenreEntities(
                        movieGenreDtos = movieGenreDtos,
                        tvGenreDtos = tvGenreDtos
                    )
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    raise(e)
                }

                try {
                    genreDao.insertAll(genreEntities)
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    raise(e)
                }
            }.onLeft { e ->
                // This will now only receive non-CancellationExceptions
                send(e.left())
            }
        } else {
            Timber.i(message = "Genre data is up to date")
        }
    }.flowOn(ioDispatcher)

    override fun getGenres(): Flow<Either<Exception, List<Genre>>> =
        getLocalGenres().map { result ->
            result.map { localGenres ->
                localGenres.map { it.toGenre() }
            }
        }

    override fun getMovieGenres(): Flow<Either<Exception, List<Genre>>> = flow<Either<Exception, List<Genre>>> {
        // emit(Absent)
    }.flowOn(ioDispatcher)

    override fun getTvGenres(): Flow<Either<Exception, List<Genre>>> = flow<Either<Exception, List<Genre>>> {
        // emit(Absent)
    }.flowOn(ioDispatcher)

    override fun getTrendingMovies(timeWindow: TimeWindow): Flow<PagingData<Movie>> {
        // TODO Some way of caching this pager?
        return trendingMoviePagerFactory.create(timeWindow)
            .flow.map { pagingData ->
                pagingData.map { it.toMovie() }
            }
    }

    override fun getRandomCuratedContentItem(): Flow<Either<Exception, CuratedContentItem?>> = flow {
        // Get locally-stored curated content
        emit(
            curatedContentDao.getRandomCuratedContentItem()?.toCuratedContentItem().right()
        )

        // TODO Maybe check timestamp of last update to avoid unnecessary scraping

        // Get the CSS href for curated content
        val cssHref = webScraper.scrapeUrlForIndexCssHref(BuildConfig.TMDB_URL)
        if (cssHref == null) {
            emit(IOException("Failed to retrieve CSS href").left())
            return@flow
        }

        // Get the locally-stored CSS href if one exists
        val localCssHref = curatedContentDao.getCuratedContentHref()
        if (cssHref != localCssHref) {
            val curatedContentEntities = webScraper.scrapeUrlForCuratedContent(
                baseUrl = BuildConfig.TMDB_URL,
                cssHref = cssHref
            ).map {
                it.toCuratedContentItemEntity()
            }

            db.withTransaction {
                curatedContentDao.clearCuratedContent()
                curatedContentDao.insertAll(curatedContentEntities)
            }

            val curatedContentItem =
                curatedContentDao.getRandomCuratedContentItem()?.toCuratedContentItem()
            curatedContentItem?.apply { emit(this.right()) }
        }
    }.flowOn(ioDispatcher)

    // endregion Methods

}
