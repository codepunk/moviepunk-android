package com.codepunk.moviepunk.di.module

import android.content.Context
import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import com.codepunk.moviepunk.BuildConfig
import com.codepunk.moviepunk.data.remote.interceptor.MoviePunkAuthInterceptor
import com.codepunk.moviepunk.data.remote.interceptor.NetworkConnectionInterceptor
import com.codepunk.moviepunk.data.remote.interceptor.UserAgentInterceptor
import com.codepunk.moviepunk.data.remote.webservice.MoviePunkWebservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    // region Methods

    @Singleton
    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, BuildConfig.OK_HTTP_CLIENT_CACHE_SIZE)

    @Singleton
    @Provides
    fun provideDiscogsOkHttpClient(
        cache: Cache,
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        userAgentInterceptor: UserAgentInterceptor,
        authInterceptor: MoviePunkAuthInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(networkConnectionInterceptor)
        .addInterceptor(userAgentInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        prettyPrint = true
        coerceInputValues = true
    }

    @Singleton
    @Provides
    fun provideEitherCallAdapterFactory(): EitherCallAdapterFactory =
        EitherCallAdapterFactory.create()

    @Singleton
    @Provides
    fun provideConverterFactory(networkJson: Json): Converter.Factory =
        networkJson.asConverterFactory("application/json".toMediaType())

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        eitherCallAdapterFactory: EitherCallAdapterFactory,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(eitherCallAdapterFactory)
        .addConverterFactory(converterFactory)
        .build()

    @Singleton
    @Provides
    fun provideHollarhypeWebService(
        retrofit: Retrofit
    ): MoviePunkWebservice = retrofit.create(MoviePunkWebservice::class.java)

    // endregion Methods

}
