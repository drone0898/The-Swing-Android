package swing.thkim.swingsample.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import swing.thkim.swingsample.data.Feed
import swing.thkim.swingsample.data.repository.DefaultFeedRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val feedRepository: DefaultFeedRepository
): ViewModel() {

    val feeds: StateFlow<List<Feed>> = feedRepository.getFavoriteFeeds()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun setFavorite(feedId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            feedRepository.updateFavoriteStatus(feedId, isFavorite)
        }
    }
}