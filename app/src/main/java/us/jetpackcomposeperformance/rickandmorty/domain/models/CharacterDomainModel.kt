package us.jetpackcomposeperformance.rickandmorty.domain.models

data class CharacterDomainModel(
  val created: String,
  val episode: List<String>,
  val gender: String,
  val id: Int,
  val image: String,
  val name: String,
  val origin: String,
  val location: String,
  val species: String,
  val status: String,
  val type: String,
  val url: String
)
