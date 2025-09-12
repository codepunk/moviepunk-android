package com.codepunk.moviepunk.di.module

import android.content.Context
import androidx.room.Room
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.dao.CrossRefDao
import com.codepunk.moviepunk.data.local.dao.GenreDao
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
    fun provideArtistDao(database: MoviePunkDatabase): GenreDao = database.genreDao()

    @Provides
    @Singleton
    fun provideCrossRefDao(database: MoviePunkDatabase): CrossRefDao = database.crossRefDao()

    // endregion Methods

}
