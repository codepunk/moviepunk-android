package com.codepunk.moviepunk.di.module

import com.codepunk.moviepunk.di.qualifier.DefaultDispatcher
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.codepunk.moviepunk.di.qualifier.MainDispatcher
import com.codepunk.moviepunk.di.qualifier.MainImmediateDispatcher
import com.codepunk.moviepunk.di.qualifier.UnconfinedDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    // region Methods

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @MainImmediateDispatcher
    @Provides
    fun provideMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate

    @UnconfinedDispatcher
    @Provides
    fun provideUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined

    // endregion Methods

}
