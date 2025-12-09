package com.codepunk.moviepunk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codepunk.moviepunk.data.local.dao.CuratedContentDao
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.local.dao.GenreMediaTypeDao
import com.codepunk.moviepunk.data.local.entity.CuratedContentItemEntity
import com.codepunk.moviepunk.data.local.entity.GenreEntity
import com.codepunk.moviepunk.data.local.entity.GenreMediaTypeEntity
import com.codepunk.moviepunk.data.local.typeconverter.CuratedContentTypeTypeConverter
import com.codepunk.moviepunk.data.local.typeconverter.InstantTypeConverter
import com.codepunk.moviepunk.data.local.typeconverter.LocalDateTypeConverter
import com.codepunk.moviepunk.data.local.typeconverter.MediaTypeTypeConverter

@Database(
    version = 1,
    entities = [
        CuratedContentItemEntity::class,
        GenreEntity::class,
        GenreMediaTypeEntity::class
    ]
)
@TypeConverters(
    value = [
        CuratedContentTypeTypeConverter::class,
        InstantTypeConverter::class,
        LocalDateTypeConverter::class,
        MediaTypeTypeConverter::class
    ]
)
abstract class MoviePunkDatabase : RoomDatabase() {

    // region Methods

    abstract fun curatedContentDao(): CuratedContentDao

    abstract fun genreDao(): GenreDao

    abstract fun genreMediaTypeDao(): GenreMediaTypeDao

    /*
    abstract fun movieDao(): MovieDao

    abstract fun movieGenreXrefDao(): MovieGenreXrefDao

    abstract fun trendingMovieDao(): TrendingMovieDao

    abstract fun trendingMovieRemoteKeyDao(): TrendingMovieRemoteKeyDao
     */

    // endregion Methods

}