package us.jetpackcomposeperformance.rickandmorty.domain.repository

import kotlinx.coroutines.flow.Flow
import us.jetpackcomposeperformance.rickandmorty.domain.models.CharacterResult

interface CharacterRepository {
  fun getCharacters(): Flow<CharacterResult>
}