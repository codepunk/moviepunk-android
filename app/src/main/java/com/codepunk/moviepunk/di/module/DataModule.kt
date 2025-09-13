package com.codepunk.moviepunk.di.module

import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import com.codepunk.moviepunk.data.repository.MoviePunkRepositoryImpl
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.codepunk.moviepunk.domain.repository.MoviePunkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    // region Methods

    @Singleton
    @Provides
    fun provideMoviePunkRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        db: MoviePunkDatabase,
        /* TODO
        connectivityManager: ConnectivityManager,
        dataStore: DataStore<UserSettings>,
         */
        webservice: MoviePunkWebservice,
    ): MoviePunkRepository = MoviePunkRepositoryImpl(
        ioDispatcher = ioDispatcher,
        db = db,
        genreDao = db.genreDao(),
        movieDao = db.movieDao(),
        /* TODO
        connectivityManager = connectivityManager,
        dataStore = dataStore,
         */
        webservice = webservice,
    )

    // endregion Methods

}