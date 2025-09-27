package com.codepunk.moviepunk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.local.dao.HashedImageDao
import com.codepunk.moviepunk.data.local.dao.MovieDao
import com.codepunk.moviepunk.data.local.dao.MovieGenreXrefDao
import com.codepunk.moviepunk.data.local.dao.TrendingMovieDao
import com.codepunk.moviepunk.data.local.dao.TrendingMovieRemoteKeyDao
import com.codepunk.moviepunk.data.local.entity.GenreEntity
import com.codepunk.moviepunk.data.local.entity.HashedImageEntity
import com.codepunk.moviepunk.data.local.entity.MovieEntity
import com.codepunk.moviepunk.data.local.entity.MovieGenreXefEntity
import com.codepunk.moviepunk.data.local.entity.TrendingMovieEntity
import com.codepunk.moviepunk.data.local.entity.TrendingMovieRemoteKeyEntity
import com.codepunk.moviepunk.data.local.typeconverter.InstantTypeConverter
import com.codepunk.moviepunk.data.local.typeconverter.LocalDateTypeConverter

@Database(
    version = 1,
    entities = [
        GenreEntity::class,
        HashedImageEntity::class,
        MovieEntity::class,
        MovieGenreXefEntity::class,
        TrendingMovieEntity::class,
        TrendingMovieRemoteKeyEntity::class
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

    abstract fun genreDao(): GenreDao

    abstract fun hashedImageDao(): HashedImageDao

    abstract fun movieDao(): MovieDao

    abstract fun movieGenreXrefDao(): MovieGenreXrefDao

    abstract fun trendingMovieDao(): TrendingMovieDao

    abstract fun trendingMovieRemoteKeyDao(): TrendingMovieRemoteKeyDao

    // endregion Methods

}