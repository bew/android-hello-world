{
  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";

  outputs = { self, nixpkgs }: let
    # Support multiple systems
    systems = [ "x86_64-linux" "aarch64-darwin" ];
    forAllSystems = nixpkgs.lib.genAttrs systems;
  in {
    devShells = forAllSystems (system: let
      pkgs = import nixpkgs {
        inherit system;
        config.allowUnfree = true;
        config.android_sdk.accept_license = true;
      };

      # NOTE: SDK 34 is Android 14
      # More versions at <https://en.wikipedia.org/wiki/Android_version_history#Overview>
      buildToolsVersion = "34.0.0";
      androidSdk = pkgs.androidenv.composeAndroidPackages {
        platformVersions = [ "34" "35" ];
        # Build tools (aapt2, apksigner, …) are versioned independently from the platform.
        buildToolsVersions = [ buildToolsVersion ];
        cmdLineToolsVersion = "latest"; # https://developer.android.com/tools/
      };
      jdk = pkgs.jdk17;
      aapt2_bin = "${androidSdk.androidsdk}/libexec/android-sdk/build-tools/${buildToolsVersion}/aapt2";
    in {
      default = pkgs.mkShell {
        __structuredAttrs = true;
        buildInputs = [
          androidSdk.androidsdk
          jdk
          pkgs.gradle
          pkgs.kotlin

          pkgs.ktlint # linter + formatter
          # LSP: https://github.com/Kotlin/kotlin-lsp
          # NOTE: It doesn't really have good Android projects support yet..
          # see: https://github.com/Kotlin/kotlin-lsp/issues/26
          # see: https://github.com/Kotlin/kotlin-lsp/issues/97
          # And even for simple use-cases like completion of imported or not-yet-imported stuff,
          # it's basically useless.. 🙁
          (pkgs.callPackage ./nix/kotlin-lsp.nix {})
          # note: The LSP is quite slow to start, easily 30s before first proof of life..

          pkgs.scrcpy
          (pkgs.callPackage ./nix/byedroid.nix {})
        ];

        env.ANDROID_HOME = "${androidSdk.androidsdk}/libexec/android-sdk";
        env.ANDROID_SDK_ROOT = "${androidSdk.androidsdk}/libexec/android-sdk";
        env.JAVA_HOME = jdk.home;

        # Force Gradle to use the Nix-patched binary (so it doesn't DL its own version!)
        env.GRADLE_OPTS = "-Dorg.gradle.project.android.aapt2FromMavenOverride=${aapt2_bin}";
      };
    });
  };
}
