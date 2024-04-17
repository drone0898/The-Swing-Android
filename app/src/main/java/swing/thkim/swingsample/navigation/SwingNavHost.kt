package swing.thkim.swingsample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import swing.thkim.swingsample.ui.compose.FavoriteScreen
import swing.thkim.swingsample.ui.compose.FeedScreen
import swing.thkim.swingsample.ui.compose.SwingSampleAppState

@Composable
fun SwingNavHost(
    appState: SwingSampleAppState,
    modifier: Modifier = Modifier,
    startDestination: String = TopLevelDestination.FEED.route
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        composable(SwingSampleRoute.FEED) {
            FeedScreen()
        }
        composable(SwingSampleRoute.FAVORITES) {
            FavoriteScreen()
        }
    }
}
