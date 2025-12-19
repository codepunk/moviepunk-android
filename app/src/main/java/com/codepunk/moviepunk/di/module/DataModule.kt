package com.codepunk.moviepunk.di.module

import android.net.ConnectivityManager
import androidx.paging.PagingConfig
import com.codepunk.moviepunk.BuildConfig
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.dao.ConfigurationDao
import com.codepunk.moviepunk.data.local.dao.CuratedContentDao
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.paging.TrendingMoviePagerFactory
import com.codepunk.moviepunk.data.remote.util.WebScraper
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
    fun providePagingConfig(): PagingConfig = PagingConfig(
        pageSize = BuildConfig.TMDB_PAGE_SIZE,
        enablePlaceholders = false,
    )

    @Singleton
    @Provides
    fun provideMoviePunkRepository(
        connectivityManager: ConnectivityManager,
        db: MoviePunkDatabase,
        configurationDao: ConfigurationDao,
        curatedContentDao: CuratedContentDao,
        genreDao: GenreDao,
        webservice: MoviePunkWebservice,
        trendingMoviePagerFactory: TrendingMoviePagerFactory,
        webScraper: WebScraper
    ): MoviePunkRepository = MoviePunkRepositoryImpl(
        connectivityManager = connectivityManager,
        db = db,
        configurationDao = configurationDao,
        curatedContentDao = curatedContentDao,
        genreDao = genreDao,
        webservice = webservice,
        trendingMoviePagerFactory = trendingMoviePagerFactory,
        webScraper = webScraper
    )

    // endregion Methods

}