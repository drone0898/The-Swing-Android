package swing.thkim.swingsample.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import swing.thkim.swingsample.navigation.SwingNavHost
import swing.thkim.swingsample.navigation.TopLevelDestination
import swing.thkim.swingsample.ui.theme.Pink40
import swing.thkim.swingsample.ui.theme.Pink80
import swing.thkim.swingsample.ui.theme.Purple40
import swing.thkim.swingsample.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwingSampleApp(appState: SwingSampleAppState) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            SwingBottomBar(
                destinations = appState.topLevelDestinations,
                onNavigateToDestination = appState::navigateTo,
                currentDestination = appState.currentDestination
            )
        },
    ) { padding ->
        Row(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            Column(Modifier.fillMaxSize()) {
                val destination = appState.currentTopLevelDestination
                val shouldShowTopAppBar = destination != null
                if (destination != null) {
                    SwingTopAppBar(
                        titleRes = destination.titleTextId,
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent,
                        )
                    )
                }
                SwingNavHost(
                    appState = appState,
                    modifier = if (shouldShowTopAppBar) {
                        Modifier.consumeWindowInsets(
                            WindowInsets.safeDrawing.only(WindowInsetsSides.Top),
                        )
                    } else {
                        Modifier
                    },
                )
            }
        }
    }
}

@Composable
private fun SwingBottomBar(
    modifier: Modifier = Modifier,
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = destination.unselectedIcon),
                        contentDescription = null,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Purple40,
                    selectedTextColor = Pink40,
                    unselectedIconColor = Purple80,
                    unselectedTextColor = Pink80
                ),
                label = { Text(stringResource(destination.iconTextId)) }
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false