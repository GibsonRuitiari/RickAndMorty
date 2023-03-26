package us.jetpackcomposeperformance.rickandmorty.ui.screens

import us.jetpackcomposeperformance.rickandmorty.domain.models.CharacterDomainModel

sealed interface RickAndMortyState{
    object Loading:RickAndMortyState
    data class Data(val characters: List<CharacterDomainModel>):RickAndMortyState{
        companion object{
            val DefaultState = Data(emptyList())
        }
    }
    @JvmInline
    value class Error(val errorMessage:String):RickAndMortyState
}
sealed interface    RickAndMortyEvents{
    object LoadCharacters:RickAndMortyEvents
    object RetryLoadingCharacters:RickAndMortyEvents
}