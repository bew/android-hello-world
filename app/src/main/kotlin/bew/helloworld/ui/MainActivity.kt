// WHAT:
// The single Activity, the OS-level entry point of the app.
// Sets up Compose as the UI layer via setContent{} and renders the root UI.
// NOTE: In modern practices, there is usually one activity and many screens that a navigation layer
// swaps out as needed.
//
// NEEDED: Yes — required entry point.
// Every Android app needs at least one Activity declared as the launcher in `AndroidManifest.xml`.

package bew.helloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import bew.helloworld.ui.theme.MyAppTheme
import bew.helloworld.ui.hello.HelloScreen
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                // For this example, we only add padding for the bottom navigation bar, not for the
                // top status bar.
                // To handle all bars, use `.systemBars` or just use a Scaffold in the Screen.
                val modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                // This surface acts as the "color-aware root" for the whole composition tree.
                // Sets `LocalContentColor` so all children (Text, Icon..) get correct fg colors.
                // Without this, text in the tree defaults to black even in dark mode.
                Surface(modifier = modifier.fillMaxSize()) {
                    HelloScreen()
                }
            }
        }
    }
}
