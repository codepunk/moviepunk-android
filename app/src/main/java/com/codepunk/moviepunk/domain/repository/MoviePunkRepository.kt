package com.codepunk.moviepunk.domain.repository

import app.cash.quiver.extensions.OutcomeOf
import com.codepunk.moviepunk.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface MoviePunkRepository {

    // region Methods

    fun getGenres(): Flow<OutcomeOf<List<Genre>>>

    fun getMovieGenres(): Flow<OutcomeOf<List<Genre>>>

    fun getTvGenres(): Flow<OutcomeOf<List<Genre>>>

    // endregion Methods

}
