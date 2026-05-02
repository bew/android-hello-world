// WHAT:
// An example helloworld screen, responsible to render the UI elements of that screen.

package bew.helloworld.ui.hello

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import timber.log.Timber

@Composable
fun HelloScreen() {
    Timber.d("HelloScreen re-composed!")
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        TopBottomElem(
            text = "Top element",
            modifier = Modifier.fillMaxWidth().background(Color.Red)
        )

        // Weighted spacers push the other components away, since there are 2 weighted spacers,
        // the column in the middle ends up in the center of the screen.
        Spacer(modifier = Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.8f),
        ) {
            HelloWorld()
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
            SimpleCounter(modifier = Modifier.fillMaxWidth(0.7f))
        }

        // The other weighted spacer
        Spacer(modifier = Modifier.weight(1f))

        TopBottomElem(
            text = "Bottom element",
            modifier = Modifier.fillMaxWidth().background(Color.Blue),
        )
    }
}

@Composable
fun HelloWorld() {
    Icon(Icons.Default.Favorite, contentDescription = "fun emoji")
    Text("Hello, World!")
    Text("👉 This seems to be working really well! 🚀")
}

@Composable
fun SimpleCounter(modifier: Modifier = Modifier) {
    // NOTE: `remember` only survives re-composition, `rememberSaveable`/`rememberSerializable` also
    // survives app switching or activity re-creation from system change (e.g. on screen rotation,
    // new font size, ..). The data is saved in the `Bundle` state of the activity.
    // ref: https://developer.android.com/develop/ui/compose/state-lifespans
    var counter by rememberSaveable { mutableStateOf(0) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Timber.d("fwiw, counter is $counter")
        Text(
            "Counter is: $counter",
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f), // take most of the space
        )
        Button(onClick = { counter += 1 }) { Text("Click me!") }
    }
}

@Composable
fun TopBottomElem(text: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.padding(3.dp),
    ) {
        for (word in text.split(" ")) {
            Text(word)
        }
    }
}
