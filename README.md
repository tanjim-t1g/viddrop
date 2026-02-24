# VidDrop Android App (Chaquopy + yt-dlp)

This repository now contains a working Android app scaffold with:

- Java UI and control layer
- Chaquopy embedded Python runtime
- `yt-dlp` installed via Gradle pip configuration
- Python wrapper API for downloads and safe command execution
- Share intent support (`ACTION_SEND` / `ACTION_VIEW`)

## Project structure

- `app/src/main/java/com/viddrop/MainActivity.java` — UI logic, download trigger, command trigger, share-link ingest.
- `app/src/main/java/com/viddrop/PyBridge.java` — Java-to-Python bridge (Chaquopy).
- `app/src/main/python/viddrop_bridge.py` — wrapper over `yt_dlp` for `download_video` and `run_command`.
- `app/src/main/res/layout/activity_main.xml` — two-pane layout matching your sketch.
- `app/build.gradle` — Chaquopy plugin + pip install (`yt-dlp`).

## Build notes

1. Open project in Android Studio (Giraffe+ recommended).
2. Ensure a local Python 3 path exists for `buildPython` in `app/build.gradle`.
3. Sync Gradle.
4. Build and run on device.

## Supported CMD input in app

- `version`
- `extractors`
- `update` (returns guidance message; update is shipped via new app build)

## Important

Use only in compliance with copyright law and platform terms.
