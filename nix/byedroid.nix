{
  lib,
  rustPlatform,
  fetchFromGitHub,
  makeWrapper,
  scrcpy,
  android-tools,
}:

rustPlatform.buildRustPackage (finalAttrs: {
  pname = "byedroid";
  version = "0.8.0";

  src = fetchFromGitHub {
    owner = "cesarferreira";
    repo = "byedroid";
    rev = "v${finalAttrs.version}";
    hash = "sha256-sPfi0C67672E+F/T2ZLUigWqIITexYvSNnNuZ7OelAo=";
  };

  cargoHash = "sha256-E6AvuPbfWrdq13zHFGlykK+vQZCZBirwquJeu1uT0ao=";

  nativeBuildInputs = [ makeWrapper ];
  # NOTE: We add additional tools to PATH, as suffix to allow environment-specific overrides
  postInstall = ''
    wrapProgram $out/bin/bd \
      --suffix PATH : ${lib.makeBinPath [ android-tools scrcpy ]}
  '';

  # Tests require a live ADB daemon and a real Android project on disk
  doCheck = false;

  meta = with lib; {
    description = "Terminal UI for Android development — build, install, run, and stream logcat";
    longDescription = ''
      byedroid replaces Android Studio's run button and log window with a keystroke-driven TUI.
      Requires `adb` (Android SDK Platform Tools) and `gradlew` at runtime.
    '';
    homepage = "https://github.com/cesarferreira/byedroid";
    changelog = "https://github.com/cesarferreira/byedroid/releases/tag/v${version}";
    license = licenses.mit;
    maintainers = with maintainers; [ bew ];
    mainProgram = "bd";
    platforms = platforms.linux ++ platforms.darwin;
  };
})
