{
  lib,
  rustPlatform,
  fetchFromGitHub,
  makeWrapper,
  scrcpy,
  android-tools,
}:

rustPlatform.buildRustPackage rec {
  pname = "byedroid";
  version = "0.7.0";

  # src = fetchFromGitHub {
  #   owner = "cesarferreira";
  #   repo = "byedroid";
  #   rev = "v${version}";
  #   hash = "sha256-rQOZljvxEAvng7WHBeKi9BsySXI3XzlnHExWDIW3PCM=";
  # };

  # Source with <cesarferreira/byedroid#PR4> (mine!)
  src = fetchFromGitHub {
    owner = "bew";
    repo = "byedroid";
    rev = "fallback-to-gradle";
    hash = "sha256-jKukThkAfWSgONMFDztGSlJTr2nTUDowjZw0FNNqtQw=";
  };

  cargoHash = "sha256-BqcqcVBV7exOFDGAYjEUySImoPHFU2jTdsyywKGb+JA=";

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
}
