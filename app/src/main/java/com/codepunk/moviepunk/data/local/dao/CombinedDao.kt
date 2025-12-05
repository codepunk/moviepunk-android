package com.codepunk.moviepunk.data.local.dao

import androidx.room.withTransaction
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.relation.GenreWithMediaTypes
import javax.inject.Inject

class CombinedDao @Inject constructor(
    private val db: MoviePunkDatabase,
    private val genreDao: GenreDao,
    private val genreMediaTypeDao: GenreMediaTypeDao
) {

    // region Methods

    // ====================
    // Genre
    // ====================

    suspend fun insert(genreWidthMediaTypes: GenreWithMediaTypes) {
        db.withTransaction {
            genreDao.insert(genreWidthMediaTypes.genre)
            genreMediaTypeDao.insertAll(genreWidthMediaTypes.mediaTypes)
        }
    }

    suspend fun insertAll(genresWithMediaTypes: List<GenreWithMediaTypes>) {
        db.withTransaction {
            genreDao.insertAll(
                genresWithMediaTypes.map { it.genre }
            )
            genreMediaTypeDao.insertAll(
                genresWithMediaTypes.map { it.mediaTypes }.flatten()
            )
        }
    }

    // endregion Methods

}