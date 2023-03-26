package us.jetpackcomposeperformance.rickandmorty.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import us.jetpackcomposeperformance.rickandmorty.domain.models.CharacterResult
import us.jetpackcomposeperformance.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

@HiltViewModel
class RickAndMortyViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
  ViewModel() {
  private val uiEvents = MutableSharedFlow<RickAndMortyEvents>(extraBufferCapacity = 10)
  private val uiState = MutableStateFlow<RickAndMortyState>(RickAndMortyState.Loading)

  val rickAndMortyState: StateFlow<RickAndMortyState>
    get() = uiState

  init {
    observeEvents()
  }

  private fun loadCharactersFromApi() {
    viewModelScope.launch {
      characterRepository.getCharacters()
        .onStart {
          uiState.value = RickAndMortyState.Loading
        }
        .catch { ex ->
          if (ex !is CancellationException) {
            uiState.value = RickAndMortyState.Error(ex.message ?: "an unknown error occurred")
          }
        }.collect {
          when (it) {
            is CharacterResult.Data -> uiState.value = RickAndMortyState.Data(it.data)
            is CharacterResult.Empty -> uiState.value = RickAndMortyState.Data.DefaultState
            is CharacterResult.Error -> uiState.value = RickAndMortyState.Error(it.message)
          }
        }
    }
  }

  fun sendEvent(uiEvent: RickAndMortyEvents) {
    uiEvents.tryEmit(uiEvent)
  }

  private fun observeEvents() {
    viewModelScope.launch {
      uiEvents.collect {
        when (it) {
          RickAndMortyEvents.LoadCharacters -> {
            loadCharactersFromApi()
          }
          RickAndMortyEvents.RetryLoadingCharacters -> {
            sendEvent(RickAndMortyEvents.LoadCharacters)
          }
        }
      }
    }
  }
}