# Web Service Explorer — Experiment 12 (HTTP Request Studio)

A premium, SaaS-inspired Android application designed to demonstrate accessing web services using HTTP requests in Android.

---

## 🌟 Overview

**Web Service Explorer** is a professionally crafted developer tool and practical experiment app that allows users to interact with live web services, execute real-time `GET` and `POST` requests, inspect parsed payloads, and view raw JSON responses with high fidelity.

---

## 🚀 Key Features

### 1. Modern SaaS UI & Design System
- **Dark Navy Theme**: Immersive dark background (`#0B0F19`) with glassmorphism surface cards and subtle borders.
- **Electric Blue & Purple Gradients**: Premium action buttons with distinct gradients for `GET` and `POST` requests.
- **Monospaced Code Viewer**: Dark code-editor style response panel for inspecting raw JSON payloads.
- **Live Status & Feedback**: Haptic feedback, toast notifications, loading indicators, and API connection status badge.

### 2. HTTP GET Request
- **API Endpoint**: `https://jsonplaceholder.typicode.com/posts/1`
- **Execution**: Performs a background `GET` request using `HttpURLConnection`.
- **Parsing**: Parses JSON responses using `JSONObject` to dynamically display:
  - User ID
  - Post ID
  - Title
  - Body / Content
- **Metrics**: Displays response time in milliseconds and HTTP status badge (`200 OK`).

### 3. HTTP POST Request
- **API Endpoint**: `https://jsonplaceholder.typicode.com/posts`
- **Execution**: Submits a structured JSON payload (`userId`, `title`, `body`) via `HttpURLConnection` (`POST` method).
- **Response**: Displays actual server response and HTTP status (`201 Created`).

### 4. Interactive Utilities
- **Copy Response**: Instantly copy raw formatted JSON to the system clipboard.
- **Clear Dashboard**: Reset the response view back to the empty state with a single tap.

---

## 🛠️ Technical Stack & Requirements

- **Language**: Java (`MainActivity.java`)
- **Layouts**: XML UI (`activity_main.xml`, custom drawables)
- **Networking**: `HttpURLConnection` executed on background threads via `ExecutorService`
- **JSON Processing**: `org.json.JSONObject`
- **UI Threading**: `runOnUiThread()`
- **Permissions**: `<uses-permission android:name="android.permission.INTERNET" />`
- **Min SDK**: 24 (Android 7.0+)
- **Compile SDK**: 37

---

## 📂 Project Structure

```tree
exp12/
├── app/
│   ├── src/main/
│   │   ├── AndroidManifest.xml
│   │   ├── java/com/example/exp12/
│   │   │   └── MainActivity.java
│   │   ├── res/
│   │   │   ├── drawable/       # Glassmorphism cards, button gradients, code viewer bg
│   │   │   ├── layout/         # activity_main.xml
│   │   │   └── values/         # colors.xml, strings.xml, themes.xml
│   └── build.gradle.kts
├── build.gradle.kts
└── README.md
```

---

## ⚙️ How to Build and Run

1. Clone or download the repository:
   ```bash
   git clone https://github.com/sandy1019s/exp-12-http-request.git
   ```
2. Open the project in **Android Studio** (Koala or newer).
3. Sync Gradle and build the project (`Build > Make Project`).
4. Run the app on an Android Emulator or physical device (requires active internet connection).
