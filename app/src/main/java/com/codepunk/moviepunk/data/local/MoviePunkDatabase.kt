package com.codepunk.moviepunk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codepunk.moviepunk.data.local.dao.GenreDao
import com.codepunk.moviepunk.data.local.entity.LocalGenre
import com.codepunk.moviepunk.data.local.entity.LocalMovieGenre
import com.codepunk.moviepunk.data.local.entity.LocalTvGenre
import com.codepunk.moviepunk.data.local.typeconverter.InstantTypeConverter

@Database(
    version = 1,
    entities = [
        LocalGenre::class,
        LocalMovieGenre::class,
        LocalTvGenre::class
    ]
)
@TypeConverters(
    value = [
        InstantTypeConverter::class
    ]
)
abstract class MoviePunkDatabase : RoomDatabase() {

    // region Methods

    abstract fun genreDao(): GenreDao

    // endregion Methods

}