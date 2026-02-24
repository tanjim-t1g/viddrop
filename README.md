# VidDrop — Complete Setup Guide
### From Zero to Working APK

---

## STEP 1 — Create the Android Studio Project

1. Open **Android Studio**
2. Click **"New Project"**
3. Choose **"Empty Views Activity"**
4. Fill in:
   - **Name:** VidDrop
   - **Package name:** com.viddrop
   - **Language:** Java
   - **Minimum SDK:** API 26 (Android 8.0)
5. Click **Finish** and wait for Gradle to sync

---

## STEP 2 — Copy All the Files

For each file below, find it in Android Studio's left panel (Project view)
and **replace its contents** by selecting all (Ctrl+A) and pasting:

```
AndroidManifest.xml
  → app/src/main/AndroidManifest.xml

activity_main.xml
  → app/src/main/res/layout/activity_main.xml

download_item.xml
  → app/src/main/res/layout/download_item.xml

dialog_terminal.xml
  → app/src/main/res/layout/dialog_terminal.xml

MainActivity.java
  → app/src/main/java/com/viddrop/MainActivity.java

YtdlpHelper.java
  → app/src/main/java/com/viddrop/YtdlpHelper.java  (NEW FILE - right click package → New → Java Class)

DownloadService.java
  → app/src/main/java/com/viddrop/DownloadService.java  (NEW FILE - same as above)

colors.xml
  → app/src/main/res/values/colors.xml

themes.xml
  → app/src/main/res/values/themes.xml

build.gradle
  → app/build.gradle  (replace existing)

All drawable XMLs → app/src/main/res/drawable/
  (Right click drawable folder → New → Drawable Resource File → paste content)
```

---

## STEP 3 — Download & Add yt-dlp Binary ⭐ MOST IMPORTANT

This is the engine of the whole app.

1. Go to: https://github.com/yt-dlp/yt-dlp/releases/latest
2. Download the file called: **`yt-dlp_linux_aarch64`**
   (This is the ARM version that works on Android phones)
3. **Rename it** to just: `yt-dlp` (no extension)
4. In Android Studio:
   - Right click on `app/src/main` folder
   - Click **New → Folder → Assets Folder**
   - Click Finish
5. Now drag the `yt-dlp` file into the **`assets`** folder

Your assets folder should look like:
```
app/src/main/assets/
  └── yt-dlp        ← the binary
```

---

## STEP 4 — Sync & Build

1. Click **File → Sync Project with Gradle Files**
2. Wait for it to finish (green bar at bottom)
3. If you see errors, click **Build → Clean Project**, then **Build → Rebuild Project**

---

## STEP 5 — Run on Your Phone

1. Enable **Developer Options** on your Android phone:
   - Settings → About Phone → tap **Build Number** 7 times
2. Enable **USB Debugging** in Developer Options
3. Plug your phone into your PC with a USB cable
4. In Android Studio, select your phone from the dropdown at the top
5. Click the **green Play ▶ button**
6. The app will install and open on your phone!

---

## STEP 6 — Test Sharing from YouTube

1. Open the YouTube app on your phone
2. Find any video
3. Tap **Share → VidDrop**
4. VidDrop opens with the URL already pasted!
5. Tap **Download Video**

---

## Common Errors & Fixes

| Error | Fix |
|-------|-----|
| `AAPT: error: resource not found` | Make sure all drawable XML files are created |
| `ClassNotFoundException: DownloadService` | Check AndroidManifest.xml has the service tag |
| `yt-dlp: permission denied` | The binary isn't executable — YtdlpHelper.setup() handles this automatically |
| `Gradle sync failed` | File → Invalidate Caches → Restart |
| App crashes on download | Make sure the yt-dlp binary is in assets/ folder |

---

## File Structure Overview

```
VidDrop/
├── app/
│   ├── src/main/
│   │   ├── assets/
│   │   │   └── yt-dlp              ← binary engine
│   │   ├── java/com/viddrop/
│   │   │   ├── MainActivity.java   ← main screen logic
│   │   │   ├── DownloadService.java ← background downloader
│   │   │   └── YtdlpHelper.java    ← runs yt-dlp commands
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml    ← main screen UI
│   │   │   │   ├── download_item.xml    ← each download card
│   │   │   │   └── dialog_terminal.xml  ← CMD popup
│   │   │   ├── drawable/            ← all bg_*.xml files
│   │   │   └── values/
│   │   │       ├── colors.xml
│   │   │       └── themes.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle
```

---

