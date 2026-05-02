// WHAT:
// Defines the project name and which modules exist.
// Also configures where Gradle looks for dependencies and build plugins.
//
// NEEDED: Yes — required.
// Without it Gradle doesn't know the project exists.
//
// MAINTAINANCE:
// Add a new `include(":module-name")` line whenever you add a new module
// (e.g. a `:core` library module).

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "HelloWorld"
include(":app")
