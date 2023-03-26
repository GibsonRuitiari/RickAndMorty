package us.jetpackcomposeperformance.rickandmorty.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import us.jetpackcomposeperformance.rickandmorty.domain.models.CharacterDomainModel

@Composable
fun RickAndMortyScreen(rickAndMortyViewModel: RickAndMortyViewModel = viewModel()) {
    rickAndMortyViewModel.sendEvent(RickAndMortyEvents.LoadCharacters)
    val uiState by rickAndMortyViewModel.rickAndMortyState.collectAsStateWithLifecycle()
    RickAndMortyScreenContent(uiState = uiState, onEvent = rickAndMortyViewModel::sendEvent)
}

@Composable
fun RickAndMortyScreenContent(uiState: RickAndMortyState, onEvent: (RickAndMortyEvents) -> Unit) {
    when (uiState) {
        is RickAndMortyState.Data -> {
            if (uiState.characters.isEmpty()) {
                RickAndMortyEmptyScreen(onEvent = onEvent)
            } else RickAndMortyView(characters = uiState.characters)
        }
        is RickAndMortyState.Error -> RickAndMortyEventsErrorScreen(
            errorMessage = uiState.errorMessage,
            onEvent = onEvent
        )
        RickAndMortyState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(40.dp))
            }
        }
    }
}

@Composable
fun RickAndMortyEventsErrorScreen(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onEvent: (RickAndMortyEvents) -> Unit
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.matchParentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = errorMessage)
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = { onEvent(RickAndMortyEvents.RetryLoadingCharacters) }) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
fun RickAndMortyEmptyScreen(modifier: Modifier = Modifier, onEvent: (RickAndMortyEvents) -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.matchParentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "There are no characters to show you unfortunately")
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = { onEvent(RickAndMortyEvents.RetryLoadingCharacters) }) {
                Text(text = "Try again")
            }
        }
    }
}

@Composable
fun RickAndMortyView(modifier: Modifier = Modifier, characters: List<CharacterDomainModel>) {
    Scaffold(modifier = modifier
      .fillMaxSize()
      .padding(WindowInsets.systemBars.asPaddingValues()), topBar = {
        RickAndMortyAppBar()
    }) {
        LazyVerticalGrid(columns = GridCells.Adaptive(150.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = it,
            content = {
                items(characters) { character ->
                    RickAndMortyComponent(characterDomainModel = character)
                }
            })
    }
}

@Composable
fun RickAndMortyAppBar(modifier: Modifier = Modifier) {
    TopAppBar(modifier = modifier) {
        Text(text = stringResource(id = us.jetpackcomposeperformance.rickandmorty.R.string.app_name))
    }
}

@Composable
fun RickAndMortyComponent(
    modifier: Modifier = Modifier,
    characterDomainModel: CharacterDomainModel
) {
    Column(modifier = modifier.width(150.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = characterDomainModel.image,
            contentDescription = null, modifier = Modifier
            .size(150.dp, 250.dp)
            .clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = characterDomainModel.name, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = characterDomainModel.location, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = characterDomainModel.status, textAlign = TextAlign.Center)
    }
}