package us.jetpackcomposeperformance.rickandmorty.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import us.jetpackcomposeperformance.rickandmorty.data.mappers.toDomainModel
import us.jetpackcomposeperformance.rickandmorty.data.network.CharactersApiService
import us.jetpackcomposeperformance.rickandmorty.data.util.safeApiCall
import us.jetpackcomposeperformance.rickandmorty.domain.models.CharacterResult
import us.jetpackcomposeperformance.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
  private val charactersApiService: CharactersApiService
) : CharacterRepository {
  override fun getCharacters(): Flow<CharacterResult> = flow {
    val response = safeApiCall {
      charactersApiService.getCharacters()
    }
    val result = if (response.isSuccess) {
      val characterResult = response.getOrNull()
      if (characterResult != null) {
        val characters = characterResult.results.map { it.toDomainModel() }.toList()
        CharacterResult.Data(data = characters)
      } else {
        CharacterResult.Empty()
      }
    } else {
      val error = response.exceptionOrNull()
      CharacterResult.Error(error?.message ?: "unknown error")
    }

    emit(result)
  }
}

