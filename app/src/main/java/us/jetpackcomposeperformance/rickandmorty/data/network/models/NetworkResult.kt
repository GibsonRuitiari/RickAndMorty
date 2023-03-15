package us.jetpackcomposeperformance.rickandmorty.data.network.models

data class NetworkResult(
    val info: Info,
    val results: List<CharacterNetworkModel>
)