package com.codepunk.moviepunk.di.module

import android.content.Context
import android.net.ConnectivityManager
import com.codepunk.moviepunk.di.qualifier.ApplicationScope
import com.codepunk.moviepunk.di.qualifier.DefaultDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // region Methods

    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Singleton
    @ApplicationScope
    @Provides
    fun provideApplicationCoroutineScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ) : CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

    // endregion Methods

}