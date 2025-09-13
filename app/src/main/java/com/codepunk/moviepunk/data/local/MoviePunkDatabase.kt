package com.codepunk.moviepunk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codepunk.moviepunk.data.local.dao.CrossRefDao
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.local.dao.MovieDao
import com.codepunk.moviepunk.data.local.entity.LocalGenre
import com.codepunk.moviepunk.data.local.entity.LocalMovie
import com.codepunk.moviepunk.data.local.entity.LocalMovieGenreCrossRef
import com.codepunk.moviepunk.data.local.typeconverter.InstantTypeConverter
import com.codepunk.moviepunk.data.local.typeconverter.LocalDateTypeConverter

@Database(
    version = 1,
    entities = [
        LocalGenre::class,
        LocalMovie::class,
        LocalMovieGenreCrossRef::class
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

    abstract fun crossRefDao(): CrossRefDao

    abstract fun genreDao(): GenreDao

    abstract fun movieDao(): MovieDao

    // endregion Methods

}