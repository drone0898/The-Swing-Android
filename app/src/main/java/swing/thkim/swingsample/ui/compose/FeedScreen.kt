package swing.thkim.swingsample.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import swing.thkim.swingsample.R
import swing.thkim.swingsample.data.Feed
import swing.thkim.swingsample.viewmodels.FeedListViewModel

@Composable
fun FeedScreen(
    viewModel: FeedListViewModel = hiltViewModel()
) {
    Column {
        val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
        val lazyPagingItems = viewModel.feeds.collectAsLazyPagingItems()
        SearchToolbar(
            searchQuery = searchQuery,
            onSearchQueryChanged = viewModel::onSearchQueryChanged
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(lazyPagingItems.itemCount) { index ->
                lazyPagingItems[index]?.let { feed ->
                    FeedItem(feed, viewModel::setFavorite)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FeedItem(feed: Feed,
             onClickFavorite: (String,Boolean) -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(180.dp)) {
        GlideImage(
            modifier = Modifier.matchParentSize(),
            model = feed.imageUrl,
            contentDescription = feed.id,
            contentScale = ContentScale.Crop)

        IconButton(
            onClick = { onClickFavorite(feed.id, !feed.favorite) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(2.dp)
        ) {
            Icon(
                imageVector = if(feed.favorite) { Icons.Default.Favorite } else { Icons.Default.FavoriteBorder },
                contentDescription = stringResource(id = R.string.favorites),
                tint = Color.Red
            )
        }
    }
}