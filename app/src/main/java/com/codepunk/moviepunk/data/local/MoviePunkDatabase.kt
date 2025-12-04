package com.codepunk.moviepunk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codepunk.moviepunk.data.local.entity.GenreEntity
import com.codepunk.moviepunk.data.local.typeconverter.InstantTypeConverter
import com.codepunk.moviepunk.data.local.typeconverter.LocalDateTypeConverter

@Database(
    version = 1,
    entities = [
        GenreEntity::class,
    ]
)
@TypeConverters(
    value = [
        InstantTypeConverter::class,
        LocalDateTypeConverter::class
    ]
)
abstract class MoviePunkDatabase : RoomDatabase() {

    // region Methods

    /*
    abstract fun curatedContentDao(): CuratedContentDao

    abstract fun genreDao(): GenreDao

    abstract fun movieDao(): MovieDao

    abstract fun movieGenreXrefDao(): MovieGenreXrefDao

    abstract fun trendingMovieDao(): TrendingMovieDao

    abstract fun trendingMovieRemoteKeyDao(): TrendingMovieRemoteKeyDao
     */

    // endregion Methods

}