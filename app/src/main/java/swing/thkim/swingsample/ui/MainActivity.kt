package swing.thkim.swingsample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import swing.thkim.swingsample.ui.compose.SwingSampleApp
import swing.thkim.swingsample.ui.compose.rememberSwingSampleAppState
import swing.thkim.swingsample.ui.theme.SwingSampleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val appState = rememberSwingSampleAppState()
            SwingSampleTheme {
                SwingSampleApp(appState = appState)
            }
        }
    }
}