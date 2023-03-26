package us.jetpackcomposeperformance.rickandmorty.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import us.jetpackcomposeperformance.rickandmorty.data.repository.CharacterRepositoryImpl
import us.jetpackcomposeperformance.rickandmorty.domain.repository.CharacterRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModules {
  @Binds
  abstract fun provideMoviesRepository(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository
}