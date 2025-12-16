package com.codepunk.moviepunk.data.paging

import com.codepunk.moviepunk.domain.model.MediaType
import com.codepunk.moviepunk.domain.model.TimeWindow
import dagger.assisted.AssistedFactory

@AssistedFactory
interface TrendingMovieRemoteMediatorFactory {

    // region Methods

    fun create(
        mediaType: MediaType = MediaType.MOVIE,
        timeWindow: TimeWindow = TimeWindow.DAY,
        pageLimit: Int
    ): TrendingMovieRemoteMediator

    // endregion Methods

}
