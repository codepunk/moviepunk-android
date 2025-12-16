package com.codepunk.moviepunk.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.dao.MovieDao
import com.codepunk.moviepunk.data.local.dao.TrendingMovieDao
import com.codepunk.moviepunk.data.local.dao.TrendingMovieRemoteKeyDao
import com.codepunk.moviepunk.data.local.entity.TrendingMovieEntity
import com.codepunk.moviepunk.data.local.entity.TrendingMovieRemoteKeyEntity
import com.codepunk.moviepunk.data.local.relation.MovieWithGenres
import com.codepunk.moviepunk.data.mapper.toMovieWithGenres
import com.codepunk.moviepunk.data.remote.util.toApiEither
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.domain.model.MediaType
import com.codepunk.moviepunk.domain.model.TimeWindow
import com.codepunk.moviepunk.domain.repository.RepositoryState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@OptIn(ExperimentalPagingApi::class)
class TrendingMovieRemoteMediator @AssistedInject constructor(
    private val movieDao: MovieDao,
    private val trendingMovieDao: TrendingMovieDao,
    private val trendingMovieRemoteKeyDao: TrendingMovieRemoteKeyDao,
    private val db: MoviePunkDatabase,
    private val webservice: MoviePunkWebservice,
    @Assisted private val mediaType: MediaType = MediaType.MOVIE,
    @Assisted private val timeWindow: TimeWindow = TimeWindow.DAY,
    @Assisted private val pageLimit: Int = 0
) : RemoteMediator<Int, MovieWithGenres>() {

    // region Methods

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieWithGenres>
    ): MediatorResult {
        // Determine the page number to load
        val page = when (loadType) {
            LoadType.REFRESH -> {
                // Start paging fresh. Try to find a remote key near the anchor position.
                // If items loaded, find nearest key, otherwise default to 1
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                val nextKey = remoteKeys?.nextKey
                nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                // Prepending, load data before the first loaded page
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                // If remoteKeys is null, that means the data is empty, so we should terminate.
                // If prevKey is null, we've reached the beginning.
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                // Appending, load data after the last loaded page
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey

                // If remoteKeys is null, that means the data is empty, so we should terminate.
                // If nextKey is null, we've reached the end.
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            // Make the network request
            val movies = webservice.fetchTrendingMovies(
                mediaType = mediaType,
                timeWindow = timeWindow,
                page = page
            ).toApiEither().fold(
                ifLeft = {
                    val throwable = when (it) {
                        is RepositoryState.ExceptionState -> it.exception
                        else -> {
                            // TODO Make this better
                            RuntimeException("An unknown error occurred.")
                        }
                    }
                    return MediatorResult.Error(throwable)
                },
                ifRight = { response -> response.results }
            )
            val endOfPaginationReached = (pageLimit > 0 && page >= pageLimit) || movies.isEmpty()

            // Save data and remote keys to database in a transaction
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    // Clear existing data and keys
                    trendingMovieDao.clearAll()
                    trendingMovieRemoteKeyDao.clearAll()
                }

                // Map to movie relations
                val moviesWithGenres = movies.map { it.toMovieWithGenres() }

                // Map to trending movies
                val trendingMovies = movies.map { movie ->
                    TrendingMovieEntity(movieId = movie.id)
                }

                // Map to remote keys
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = movies.map { movie ->
                    TrendingMovieRemoteKeyEntity(
                        movieId = movie.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                // Insert everything
                movieDao.insertAllWithGenres(moviesWithGenres)
                trendingMovieDao.insertAll(trendingMovies)
                trendingMovieRemoteKeyDao.insertAll(remoteKeys)
            }

            // Return success
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    /**
     * Helper method to get the [TrendingMovieRemoteKeyEntity] closest to the anchor position
     * supplied by [state].
     */
    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieWithGenres>
    ): TrendingMovieRemoteKeyEntity? = state.anchorPosition?.let { anchorPosition ->
        state.closestItemToPosition(anchorPosition)?.let {
            trendingMovieRemoteKeyDao.getByMovieId(it.movie.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieWithGenres>
    ): TrendingMovieRemoteKeyEntity? = state.firstItemOrNull()?.let {
        trendingMovieRemoteKeyDao.getByMovieId(it.movie.id)
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieWithGenres>
    ): TrendingMovieRemoteKeyEntity? = state.lastItemOrNull()?.let {
        trendingMovieRemoteKeyDao.getByMovieId(it.movie.id)
    }

    // endregion Methods

}
