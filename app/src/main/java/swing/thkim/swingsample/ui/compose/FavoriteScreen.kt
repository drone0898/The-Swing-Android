package swing.thkim.swingsample.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import swing.thkim.swingsample.viewmodels.FavoriteListViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoriteListViewModel = hiltViewModel()
) {
    Column {
        val favorites = viewModel.feeds.collectAsStateWithLifecycle()
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favorites.value.size) { index ->
                FeedItem(favorites.value[index], viewModel::setFavorite)
            }
        }
    }
}