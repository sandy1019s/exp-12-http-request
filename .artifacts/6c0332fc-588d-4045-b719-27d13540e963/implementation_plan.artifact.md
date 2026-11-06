# Modern UI Upgrade – Web Service Explorer (Experiment 12) Implementation Plan

Design and implement a premium, SaaS-inspired Android application called **Web Service Explorer** for experimenting with HTTP requests using Java, HttpURLConnection, ExecutorService, and JSON parsing.

## User Review Required

> [!IMPORTANT]
> - Implementation language: **Java** (`MainActivity.java`) with XML layouts (`activity_main.xml`).
> - Background networking: `ExecutorService` with `HttpURLConnection`.
> - JSON processing: `JSONObject` and `JSONArray`.
> - UI theme: Dark SaaS-inspired theme (`#0B0F19` dark navy background, electric blue `#3B82F6` and purple `#8B5CF6` accents, glassmorphism cards, monospaced response viewer).

## Open Questions

- None. All requirements are clearly defined.

## Proposed Changes

### Android Manifest & Gradle
#### [MODIFY] [AndroidManifest.xml](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/AndroidManifest.xml)
- Add `<uses-permission android:name="android.permission.INTERNET" />`.

### Resources & Styling
#### [NEW] [colors.xml](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/res/values/colors.xml)
- Define custom dark mode palette: primary dark (`#0B0F19`), surface card (`#161E2E`), border (`#1E293B`), primary blue (`#3B82F6`), accent purple (`#8B5CF6`), success green (`#10B981`), text primary (`#F8FAFC`), text secondary (`#94A3B8`).

#### [NEW] [themes.xml](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/res/values/themes.xml)
- Configure Material 3 NoActionBar theme with dark background and status bar styling.

#### [NEW] [activity_main.xml](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/res/layout/activity_main.xml)
- Build the complete layout using `NestedScrollView`, ConstraintLayout, card views with rounded corners, hero header, request control buttons (GET & POST), structured parsed response view (User ID, Post ID, Title, Body cards), raw JSON code editor viewer, copy/clear buttons, and progress bar overlay.

#### [NEW] [drawable/bg_card.xml](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/res/drawable/bg_card.xml)
- Glassmorphism/card background with rounded corners and subtle border.

#### [NEW] [drawable/bg_get_button.xml](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/res/drawable/bg_get_button.xml)
- Electric blue gradient for GET request button.

#### [NEW] [drawable/bg_post_button.xml](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/res/drawable/bg_post_button.xml)
- Purple gradient for POST request button.

#### [NEW] [drawable/bg_code_viewer.xml](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/res/drawable/bg_code_viewer.xml)
- Dark code editor container background.

### Source Code
#### [DELETE] [MainActivity.kt](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/java/com/example/exp12/MainActivity.kt)
- Remove Kotlin main activity.

#### [NEW] [MainActivity.java](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/java/com/example/exp12/MainActivity.java)
- Implement `MainActivity` in Java:
  - Initialize UI components (buttons, text views, response cards, progress indicator, status badges).
  - Setup `ExecutorService` for background networking.
  - Implement GET request handler (`https://jsonplaceholder.typicode.com/posts/1`) using `HttpURLConnection`, parse response with `JSONObject`, update UI via `runOnUiThread()`.
  - Implement POST request handler (`https://jsonplaceholder.typicode.com/posts`) with payload (`userId`, `title`, `body`), parse response, display status 201 Created and server response.
  - Add Copy to Clipboard functionality and Clear Response functionality.
  - Handle network errors gracefully with toasts and error states.

## Verification Plan

### Automated Tests
- Build project using Gradle (`app:assembleDebug`) to ensure compilation success.

### Manual Verification
- Deploy and verify UI rendering and execution of GET and POST requests on emulator/device.
