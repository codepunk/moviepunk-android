package com.codepunk.moviepunk.di.module

import android.content.Context
import androidx.room.Room
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.dao.ConfigurationDao
import com.codepunk.moviepunk.data.local.dao.CuratedContentDao
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.local.dao.GenreMediaTypeDao
import com.codepunk.moviepunk.data.local.dao.MovieDao
import com.codepunk.moviepunk.data.local.dao.MovieGenreXrefDao
import com.codepunk.moviepunk.data.local.dao.TrendingMovieDao
import com.codepunk.moviepunk.data.local.dao.TrendingMovieRemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    // region Methods

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MoviePunkDatabase =
        Room.databaseBuilder(
            context = context,
            klass = MoviePunkDatabase::class.java,
            name = "moviepunk_db"
        ).build()

    @Provides
    @Singleton
    fun provideConfigurationDao(
        database: MoviePunkDatabase
    ): ConfigurationDao = database.configurationDao()

    @Provides
    @Singleton
    fun provideCuratedContentDao(
        database: MoviePunkDatabase
    ): CuratedContentDao = database.curatedContentDao()

    @Provides
    @Singleton
    fun provideGenreDao(database: MoviePunkDatabase): GenreDao = database.genreDao()

    @Provides
    @Singleton
    fun provideGenreMediaTypeDao(database: MoviePunkDatabase): GenreMediaTypeDao =
        database.genreMediaTypeDao()

    @Provides
    @Singleton
    fun provideMovieDao(database: MoviePunkDatabase): MovieDao = database.movieDao()

    @Provides
    @Singleton
    fun provideMovieGenreXrefDao(
        database: MoviePunkDatabase
    ): MovieGenreXrefDao = database.movieGenreXrefDao()

    @Provides
    @Singleton
    fun provideTrendingMovieDao(
        database: MoviePunkDatabase
    ): TrendingMovieDao = database.trendingMovieDao()

    @Provides
    @Singleton
    fun provideTrendingMovieRemoteKeyDao(
        database: MoviePunkDatabase
    ): TrendingMovieRemoteKeyDao = database.trendingMovieRemoteKeyDao()

    // endregion Methods

}
