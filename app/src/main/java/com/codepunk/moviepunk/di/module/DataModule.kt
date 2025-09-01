package com.codepunk.moviepunk.di.module

import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.data.repository.MoviePunkRepositoryImpl
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    // region Methods

    @Singleton
    @Provides
    fun provideMoviePunkRepository(
        /* TODO
        connectivityManager: ConnectivityManager,
        database: HollarhypeDatabase,
        dataStore: DataStore<UserSettings>,
         */
        webservice: MoviePunkWebservice
        /* TODO ,
        activityFeedRemoteMediatorFactory: ActivityFeedRemoteMediatorFactory
         */
    ): MoviePunkRepository = MoviePunkRepositoryImpl(
        /* TODO
        connectivityManager = connectivityManager,
        dataStore = dataStore,
        database = database,
         */
        webservice = webservice,
        /*
        activityFeedRemoteMediatorFactory = activityFeedRemoteMediatorFactory
         */
    )

    // endregion Methods

}