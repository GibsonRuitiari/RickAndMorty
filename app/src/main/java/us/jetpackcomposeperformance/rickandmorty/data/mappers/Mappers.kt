package us.jetpackcomposeperformance.rickandmorty.data.mappers

import us.jetpackcomposeperformance.rickandmorty.data.network.models.CharacterNetworkModel
import us.jetpackcomposeperformance.rickandmorty.domain.models.CharacterDomainModel

fun CharacterNetworkModel.toDomainModel() =
  CharacterDomainModel(
    id = this.id ?: 0,
    name = this.name ?: "Rick Sanchez",
    location = this.location?.name ?: "Citadel of Ricks",
    origin = this.origin?.name ?: "Earth (C-137)",
    status = this.status ?: "Alive",
    species = this.species ?: "Human",
    episode = this.episode,
    type = this.type ?: "",
    gender = this.gender ?: "Male",
    image = this.image ?: "https://rickandmortyapi.com/api/character/avatar/11.jpeg",
    created = this.created ?: "2017-11-04T18:48:46.250Z",
    url = this.url ?: "https://rickandmortyapi.com/api/character/1",
  )