_default:
  just --list

pkg-name := "bew.helloworld"

# Build debug APK
build:
  gradle assembleDebug

# Build and install debug APK on connected device/emulator
install:
  gradle installDebug

# Run the app!
run DISPLAY-ID="":
  #!/usr/bin/env bash
  extra_args=()
  DISPLAY_ID="{{DISPLAY-ID}}"
  if [[ -n "$DISPLAY_ID" ]]; then
    echo "App will start on display $DISPLAY_ID"
    extra_args+=(--display "$DISPLAY_ID")
  fi
  # (note: `am` or `pm` are thin wrappers around `cmd <somecommand>`)
  adb shell cmd activity start -n {{pkg-name}}/.MainActivity "${extra_args[@]}"

# Install and run the app!
install-run DISPLAY-ID="": install
  @echo # blank line
  just run "{{DISPLAY-ID}}"

# Open a window of a separate phone display (phone can stay screen off!)
start-ui:
  scrcpy --no-audio --new-display --stay-awake
  @ # warn: --stay-awake only works when connected via USB,
  @ # otherwise you need to keep the actual phone awake.
  @ # 👉 The real solution is `--keep-active` from <Genymobile/scrcpy#PR6787>
  @ # (not released yet @2026-05-01)

# Show all logs of the running app (filter by MYAPP)
logs:
  @ # adb logcat --pid=$(adb shell pidof -s {{pkg-name}}) -v color | grep MYAPP
  adb logcat -v color | grep MYAPP
alias l := logs

# -----------------------------------------------------------------

rebuild-classes-jar:
  # This seems to help `kotlin-lsp` 🤔 (it logs a warning when it needs it!)
  gradle :app:bundleDebugClassesToCompileJar

# Run all tests
test:
  gradle test

# Run linter (via ktlint)
lint:
  ktlint

# Run formatter (via ktlint)
fmt:
  ktlint -F

# Clean build artifacts
clean:
  gradle clean
