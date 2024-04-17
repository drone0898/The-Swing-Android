package swing.thkim.swingsample.navigation

import androidx.annotation.DrawableRes
import swing.thkim.swingsample.R

enum class TopLevelDestination (
    val route : String,
    @DrawableRes
    val unselectedIcon: Int,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    FEED(
        route = SwingSampleRoute.FEED,
        unselectedIcon = R.drawable.round_home_off_24,
        iconTextId = R.string.feed,
        titleTextId = R.string.feed,
    ),
    FAVORITES(
        route = SwingSampleRoute.FAVORITES,
        unselectedIcon = R.drawable.favorite_off_24,
        iconTextId = R.string.favorites,
        titleTextId = R.string.favorites,
    )
}