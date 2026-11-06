# Walkthrough – Modern UI Upgrade: Web Service Explorer (Experiment 12)

Designed and implemented a premium SaaS-inspired mobile interface for the **Web Service Explorer** application (Experiment 12: Access Web Services Using HTTP Requests in Android).

## Changes Made

### Configuration & Permissions
- **[AndroidManifest.xml](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/AndroidManifest.xml)**: Added `android.permission.INTERNET` permission.
- **[build.gradle.kts](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/build.gradle.kts)**: Updated `compileSdk` and `targetSdk` to 37 to support modern Android dependencies.

### Styling & Resources
- **[colors.xml](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/res/values/colors.xml)**: Created a dark SaaS color palette featuring deep navy background (`#0B0F19`), surface cards (`#161E2E`), electric blue (`#3B82F6`), purple accents (`#8B5CF6`), success green (`#10B981`), and dark monospaced code viewer backgrounds (`#0D1322`).
- **[themes.xml](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/res/values/themes.xml)**: Configured Material 3 NoActionBar theme with custom status bar and navigation bar coloring.
- **Drawables**: Created glassmorphism card backgrounds (`bg_card.xml`), electric blue gradient GET button (`bg_get_button.xml`), purple gradient POST button (`bg_post_button.xml`), and code editor backgrounds (`bg_code_viewer.xml`).

### Layout
- **[activity_main.xml](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/res/layout/activity_main.xml)**:
  - Top navigation bar with logo, app name "Web Service Explorer", "API Connected" status indicator, and subtitle.
  - Hero section titled "HTTP Request Studio" with supporting text.
  - Request Control Panel card featuring interactive GET and POST request buttons with method badges.
  - Response Dashboard featuring status badges (200 OK / 201 Created), response time in milliseconds, HTTP method indicator, structured payload cards (User ID, Post ID, Title, Body), collapsible monospaced raw JSON code viewer, copy response button, and clear button.

### Source Code
- **[MainActivity.java](file:///C:/Users/santh/AndroidStudioProjects/exp12/app/src/main/java/com/example/exp12/MainActivity.java)**:
  - Implemented background networking using `ExecutorService`.
  - Implemented `HttpURLConnection` GET requests (`https://jsonplaceholder.typicode.com/posts/1`) and POST requests (`https://jsonplaceholder.typicode.com/posts`) with JSON payload (`userId`, `title`, `body`).
  - Parsed responses using `JSONObject`.
  - Updated UI safely using `runOnUiThread()`.
  - Added clipboard copy functionality and toast notifications.
- Removed legacy `MainActivity.kt`.

## Verification Results

### Automated Verification
- Executed Gradle build (`app:assembleDebug`) successfully with status **BUILD SUCCESSFUL**.
