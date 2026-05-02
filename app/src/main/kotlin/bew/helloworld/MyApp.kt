// WHAT:
// Custom Application subclass, the first code that runs when the app process starts.
//
// NEEDED: Required to run any app-wide initialisation before Activities start.
// Declared via `android:name=".MyApp"` in AndroidManifest.xml.
//
// MAINTENANCE:
// Add any other one-time app-wide setup here (e.g. dependency injection, crash reporting).

package bew.helloworld

import android.app.Application
import timber.log.Timber

// A custom logger that adds a tag prefix to all logs, to ease logs filtering.
class PrefixedDebugTree(
    private val prefix: String,
) : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? =
        "$prefix/${super.createStackElementTag(element)}"
}

val DEBUG = true

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (DEBUG) {
            Timber.plant(PrefixedDebugTree("MYAPP"))
        }
        Timber.d("The app started!")
    }
}
