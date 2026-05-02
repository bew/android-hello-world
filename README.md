# Hello World app

A simple Android app built with Jetpack Compose.

Features:
- Full Nix-based dev environment
- `just`-based project helpers to build/install/run/watch the app/watch logs
- Auto dark/light colorscheme based on the system
- Logging setup using `Timber`, initialized early
- Setup with `MainActivity`, `MainScreen`, and a few components
- A `SimpleCounter` component with _internal_ state
- Comments everywhere explaining things

## Requirements

- Nix (with flakes enabled)
- An Android phone for testing (See [./DEVICE_SETUP.md](./DEVICE_SETUP.md))

## Setup

```sh
nix develop
```

## Build & Run

```sh
just build    # build (debug APK)
just install  # (build+)install on connected device
just run      # Start the app on the connected device
```

## Virtual display on the computer using [`scrcpy`][scrcpy]

[scrcpy]: https://github.com/Genymobile/scrcpy

Using [`scrcpy`][scrcpy] we can create a virtual display on the phone (separate from its physical
screen), which opens as a dedicated window on your computer.

```sh
# Start the virtual display
just start-ui
# Note the DISPLAY_ID at the end of the log line about the new display:
# `[server] INFO: New display: 1116x2484/372 (id=33)` 👉 DISPLAY_ID=33

# Now you can run the app on the virtual display
just run DISPLAY_ID
# Or (build+)install+run
just install-run DISPLAY_ID
```
Example for `id=5`: `just run 5`

> [!NOTE]
> If the virtual display goes black, close the window & `just start-ui` again.
> (see notes in `justfile`)
