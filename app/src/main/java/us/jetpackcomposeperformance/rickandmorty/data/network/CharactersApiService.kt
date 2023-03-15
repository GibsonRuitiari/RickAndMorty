package us.jetpackcomposeperformance.rickandmorty.data.network

import retrofit2.http.GET
import us.jetpackcomposeperformance.rickandmorty.data.network.models.NetworkResult

interface CharactersApiService {
  @GET("character")
  suspend fun getCharacters(): NetworkResult
}