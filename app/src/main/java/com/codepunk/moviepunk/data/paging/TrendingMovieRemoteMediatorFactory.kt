package com.codepunk.moviepunk.data.paging

import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.dao.MultiDao
import com.codepunk.moviepunk.data.local.dao.TrendingMovieDao
import com.codepunk.moviepunk.data.local.dao.TrendingMovieRemoteKeyDao
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.domain.model.EntityType
import com.codepunk.moviepunk.domain.model.TimeWindow
import javax.inject.Inject

class TrendingMovieRemoteMediatorFactory @Inject constructor(
    private val multiDao: MultiDao,
    private val trendingMovieDao: TrendingMovieDao,
    private val trendingMovieRemoteKeyDao: TrendingMovieRemoteKeyDao,
    private val database: MoviePunkDatabase,
    private val webservice: MoviePunkWebservice
) {

    fun create(
        entityType: EntityType = EntityType.MOVIE,
        timeWindow: TimeWindow = TimeWindow.DAY,
    ): TrendingMovieRemoteMediator = TrendingMovieRemoteMediator(
        entityType = entityType,
        timeWindow = timeWindow,
        multiDao = multiDao,
        trendingMovieDao = trendingMovieDao,
        trendingMovieRemoteKeyDao = trendingMovieRemoteKeyDao,
        database = database,
        webservice = webservice

    )

}
