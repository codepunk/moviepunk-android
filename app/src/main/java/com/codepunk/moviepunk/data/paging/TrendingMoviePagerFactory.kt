package com.codepunk.moviepunk.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.codepunk.moviepunk.data.local.dao.MovieDao
import com.codepunk.moviepunk.data.local.relation.MovieWithGenres
import com.codepunk.moviepunk.domain.model.EntityType
import com.codepunk.moviepunk.domain.model.TimeWindow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class TrendingMoviePagerFactory @Inject constructor(
    private val pagingConfig: PagingConfig,
    private val remoteMediatorFactory: TrendingMovieRemoteMediatorFactory,
    private val movieDao: MovieDao
) {

    // region Methods

    fun get(timeWindow: TimeWindow): Pager<Int, MovieWithGenres> = Pager(
        config = pagingConfig,
        remoteMediator = remoteMediatorFactory.create(
            entityType = EntityType.MOVIE,
            timeWindow = timeWindow
        ),
        pagingSourceFactory = {
            movieDao.getTrendingMoviePagingSource()
        }
    )

    // endregion Methods

}
