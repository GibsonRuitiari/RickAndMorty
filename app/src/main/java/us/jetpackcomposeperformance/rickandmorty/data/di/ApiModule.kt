package us.jetpackcomposeperformance.rickandmorty.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import us.jetpackcomposeperformance.rickandmorty.data.network.CharactersApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

  @Singleton
  @Provides
  fun provideRetrofit(
    converter: MoshiConverterFactory,
    okHttpClient: OkHttpClient
  ): Retrofit =
    Retrofit.Builder()
      .baseUrl(" https://rickandmortyapi.com/api/")
      .addConverterFactory(converter)
      .client(okHttpClient)
      .build()

  @Singleton
  @Provides
  fun provideConverterFactory() = MoshiConverterFactory.create()

  @Singleton
  @Provides
  fun provideHttpClient(
    loggingInterceptor: HttpLoggingInterceptor
  ) = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

  @Singleton
  @Provides
  fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
  }

  @Singleton
  @Provides
  fun provideApiService(retrofit: Retrofit) = retrofit.create(CharactersApiService::class.java)
}
