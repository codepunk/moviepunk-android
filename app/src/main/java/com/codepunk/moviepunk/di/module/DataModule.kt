package com.codepunk.moviepunk.di.module

import android.content.Context
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.data.repository.MoviePunkRepositoryImpl
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    // region Methods

    @Singleton
    @Provides
    fun provideMoviePunkRepository(
        @ApplicationContext context: Context,
        db: MoviePunkDatabase,
        /* TODO
        connectivityManager: ConnectivityManager,
        dataStore: DataStore<UserSettings>,
         */
        webservice: MoviePunkWebservice
        /* TODO ,
        activityFeedRemoteMediatorFactory: ActivityFeedRemoteMediatorFactory
         */
    ): MoviePunkRepository = MoviePunkRepositoryImpl(
        context = context,
        db = db,
        genreDao = db.genreDao(),
        movieDao = db.movieDao(),
        /* TODO
        connectivityManager = connectivityManager,
        dataStore = dataStore,
         */
        webservice = webservice,
        /*
        activityFeedRemoteMediatorFactory = activityFeedRemoteMediatorFactory
         */
    )

    // endregion Methods

}