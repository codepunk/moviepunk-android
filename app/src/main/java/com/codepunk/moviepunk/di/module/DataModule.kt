package com.codepunk.moviepunk.di.module

import android.net.ConnectivityManager
import androidx.paging.PagingConfig
import com.codepunk.moviepunk.BuildConfig
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.paging.TrendingMoviePagerFactory
import com.codepunk.moviepunk.data.remote.util.WebScraper
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
    fun providePagingConfig(): PagingConfig = PagingConfig(
        pageSize = BuildConfig.TMDB_PAGE_SIZE,
        enablePlaceholders = false,
    )

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
        trendingMoviePagerFactory: TrendingMoviePagerFactory,
        webScraper: WebScraper,
        connectivityManager: ConnectivityManager
    ): MoviePunkRepository = MoviePunkRepositoryImpl(
        ioDispatcher = ioDispatcher,
        curatedContentDao = db.curatedContentDao(),
        genreDao = db.genreDao(),
        movieDao = db.movieDao(),
        /* TODO
        connectivityManager = connectivityManager,
        dataStore = dataStore,
         */
        db = db,
        webservice = webservice,
        trendingMoviePagerFactory = trendingMoviePagerFactory,
        webScraper = webScraper,
        connectivityManager = connectivityManager
    )

    // endregion Methods

}