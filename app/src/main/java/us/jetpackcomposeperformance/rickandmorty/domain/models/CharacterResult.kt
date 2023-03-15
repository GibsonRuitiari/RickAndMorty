package us.jetpackcomposeperformance.rickandmorty.domain.models

sealed class CharacterResult {
  data class Empty(val data:List<CharacterDomainModel> = emptyList()): CharacterResult()
  data class Data(val data: List<CharacterDomainModel>): CharacterResult()
  data class Error(val message: String): CharacterResult()
}