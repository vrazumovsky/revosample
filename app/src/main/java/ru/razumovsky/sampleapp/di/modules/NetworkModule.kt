package ru.razumovsky.sampleapp.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.razumovsky.sampleapp.BuildConfig
import ru.razumovsky.sampleapp.data.network.UrlProvider
import ru.razumovsky.sampleapp.data.network.UrlProviderImpl
import ru.razumovsky.sampleapp.di.scope.AppScope

@Module
class NetworkModule {

    @Provides
    @AppScope
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @AppScope
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()


    @Provides
    @AppScope
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @AppScope
    fun provideUrlProvider(urlProvider: UrlProviderImpl): UrlProvider = urlProvider

    @Provides
    @AppScope
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient,
        urlProvider: UrlProvider
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(urlProvider.getBase())
        .client(okHttpClient)
        .build()

}