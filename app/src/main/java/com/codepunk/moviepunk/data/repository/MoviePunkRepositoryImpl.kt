package com.codepunk.moviepunk.data.repository

import android.net.ConnectivityManager
import arrow.core.Either
import com.codepunk.moviepunk.data.remote.util.WebScraper
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.MediaType
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import com.codepunk.moviepunk.domain.repository.RepoFailure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviePunkRepositoryImpl(
    private val connectivityManager: ConnectivityManager,
    private val ioDispatcher: CoroutineDispatcher,
    /*
    private val curatedContentDao: CuratedContentDao,
    private val db: MoviePunkDatabase,
    private val genreDao: GenreDao,
    private val movieDao: MovieDao,
    private val webservice: MoviePunkWebservice,
    private val trendingMoviePagerFactory: TrendingMoviePagerFactory,
     */
    private val webScraper: WebScraper
) : MoviePunkRepository {

    // region Methods

    /*
    override suspend fun syncGenresNew(): Either<RepoFailure, Boolean> =
        networkBoundResultOld<
                List<GenreEntity>,
                Map<MediaType, Response<GenreListResponse>>,
                Either<RepoFailure, Boolean>
        >(
            query = { genreDao.getAll() },
            fetch = {
                mapOf(
                    MOVIE to webservice.fetchGenres(MOVIE),
                    TV to webservice.fetchGenres(TV)
                )
            },
            saveFetchResult = { result ->
                either {
                    val movieGenreDtos = result.getValue(MOVIE).toApiEither().bind().genres
                    val tvGenreDtos = result.getValue(TV).toApiEither().bind().genres
                    combineToGenreEntities(movieGenreDtos, tvGenreDtos)
                }.map { entities ->
                    // This map is basically a transform
                    db.withTransaction {
                        genreDao.deleteAll()
                        genreDao.insertAll(entities)
                    }.isNotEmpty()
                }.apply {
                    this
                }
            },
            shouldFetch = {
                // THIS method needs to be able to stop the proceedings and signal an error
                true
            },
            transform = { entities -> false.right() }
        )
     */

    /*
     * This is a tricky method because we kind of need to fetch and save ALL genres first
     * (so we know which ones are moves, tv etc.) before we can return just the movies, for example
     */
    private suspend fun getGenres(type: MediaType): Either<RepoFailure, List<Genre>> = TODO()

    /*
    override fun syncGenres(): Flow<Either<RepoFailure, Boolean>> {
        var dataUpdated = false
        return networkBoundResource(
            coroutineContext = ioDispatcher,
            query = { genreDao.getAll() },
            fetch = {
                mapOf(
                    MOVIE to webservice.fetchGenres(MOVIE),
                    TV to webservice.fetchGenres(TV)
                )
            },
            saveFetchResult = { result ->
                dataUpdated = either {
                    val movieGenreDtos = result.getValue(MOVIE).toApiEither().bind().genres
                    val tvGenreDtos = result.getValue(TV).toApiEither().bind().genres
                    combineToGenreEntities(movieGenreDtos, tvGenreDtos)
                }.onLeft { failure ->
                    emit(failure.left())
                }.onRight { entities ->
                    // Here, we updated the data
                    db.withTransaction {
                        genreDao.deleteAll()
                        genreDao.insertAll(entities)
                    }.isNotEmpty()
                }.fold(
                    ifLeft = { false },
                    ifRight = { it.isNotEmpty() }
                )
            },
            shouldFetch = { entities ->
                when {
                    !connectivityManager.isConnected -> {
                        emit(NoConnectivityFailure.left())
                        false
                    }
                    entities.isEmpty() -> true
                    else -> {
                        val duration = BuildConfig.DATA_REFRESH_DURATION_MINUTES.minutes
                        val oldest = entities.minBy { it.createdAt }
                        Clock.System.now() - oldest.createdAt > duration
                    }
                }
            },
            shouldEmitInitialData = { false },
            transform = { dataUpdated.right() }
        )
    }
     */

    override fun syncCuratedContent(): Flow<Either<RepoFailure, Boolean>> = flow {
        /*
        var networkCssHref = ""
        var dataUpdated = false
        return networkBoundResource(
            coroutineContext = ioDispatcher,
            query = { curatedContentDao.getAll() },
            fetch = {
                webScraper.scrapeUrlForCuratedContent(
                    baseUrl = BuildConfig.TMDB_URL,
                    cssHref = networkCssHref
                )
            },
            saveFetchResult = {result ->
                dataUpdated = either {
                    result.toApiEither().bind().content.map { it.toCuratedContentItemEntity() }
                }.onLeft { failure ->
                    emit(failure.left())
                }.onRight { entities ->
                    // Here, we updated the data
                    db.withTransaction {
                        curatedContentDao.deleteAll()
                        curatedContentDao.insertAll(entities)
                    }.isNotEmpty()
                }.fold(
                    ifLeft = { false },
                    ifRight = { it.isNotEmpty() }
                )
            },
            shouldFetch = { content ->
                if (!connectivityManager.isConnected) {
                    emit(NoConnectivityFailure.left())
                    false
                } else {
                    either {
                        // Get the CSS href for curated content
                        networkCssHref = try {
                            webScraper.scrapeUrlForIndexCssHref(BuildConfig.TMDB_URL)
                        } catch (e: Exception) {
                            raise(ExceptionFailure(e))
                        } ?: raise(
                            ExceptionFailure(IOException(CSS_MESSAGE))
                        )

                        // Check whether the network uses a new href
                        val localCssHref = content.firstOrNull()?.href.orEmpty()
                        localCssHref != networkCssHref
                    }.onLeft {
                        emit(it.left())
                    }.getOrElse { false }
                }
            },
            shouldEmitInitialData = { false },
            transform = { dataUpdated.right() }
        )
         */
    }

    /*
    override fun getTrendingMovies(timeWindow: TimeWindow): Flow<PagingData<Movie>> {
        // TODO Some way of caching this pager?
        return trendingMoviePagerFactory.create(timeWindow)
            .flow.map { pagingData ->
                pagingData.map { it.toMovie() }
            }
    }

    override fun getRandomCuratedContentItem(): Flow<Either<RepoFailure, CuratedContentItem?>> = flow {
        // TODO NEXT ("Not yet implemented")
    }
     */

    // endregion Methods

    // region Companion object

    private companion object {
        private const val CSS_MESSAGE = "Failed to retrieve CSS href"
    }

    // endregion Companion object

}
