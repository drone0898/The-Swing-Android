package swing.thkim.swingsample.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import swing.thkim.swingsample.data.Feed
import swing.thkim.swingsample.data.repository.DefaultFeedRepository
import javax.inject.Inject

@HiltViewModel
class FeedListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val feedRepository: DefaultFeedRepository
): ViewModel() {
    val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "Electric Scooters")

    init {
        viewModelScope.launch {
            searchQuery.filter { it.length >= SEARCH_QUERY_MIN_LENGTH }.collectLatest {
                feedRepository.clearFeeds()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val feeds: Flow<PagingData<Feed>> = searchQuery
        .filter { it.length >= SEARCH_QUERY_MIN_LENGTH }
        .flatMapLatest { query ->
            feedRepository.getSearchResultStream(query).cachedIn(viewModelScope)
        }

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }

    fun setFavorite(feedId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            feedRepository.updateFavoriteStatus(feedId, isFavorite)
        }
    }

    companion object {
        private const val SEARCH_QUERY_MIN_LENGTH = 2
        private const val SEARCH_QUERY = "searchQuery"
    }
}