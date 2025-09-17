package com.codepunk.moviepunk.data.paging

import com.codepunk.moviepunk.domain.model.EntityType
import com.codepunk.moviepunk.domain.model.TimeWindow
import dagger.assisted.AssistedFactory

@AssistedFactory
interface TrendingMovieRemoteMediatorFactory {

    // region Methods

    fun create(
        entityType: EntityType = EntityType.MOVIE,
        timeWindow: TimeWindow = TimeWindow.DAY,
    ): TrendingMovieRemoteMediator

    // endregion Methods

}
