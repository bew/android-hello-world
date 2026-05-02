// WHAT:
// This composable is a 'theme' that wraps all app UI.
// Uses Material3 defaults with dynamic color (Material You) on Android 12+, plain Material3
// defaults on older devices.
//
// NEEDED: Yes.
// This is what applies Material3 styling throughout the entire app.
//
// MAINTAINANCE:
// To add brand colors, define color schemes here directly.

package bew.helloworld.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
        content = content,
    )
}
