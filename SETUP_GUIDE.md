# TelegramBotApp - Complete Setup Guide

## 🚀 Quick Start

### Prerequisites
- Android Studio Flamingo or later
- JDK 17+
- Android SDK 35
- Gradle 8.2+

### Step 1: Clone Repository
```bash
git clone https://github.com/amazon129810-byte/TelegramBotApp.git
cd TelegramBotApp
```

### Step 2: Configure Environment

Create `local.properties`:
```properties
sdk.dir=/path/to/android/sdk
TELEGRAM_BOT_TOKEN=your_telegram_bot_token_here
API_KEY=your_api_key
KEYSTORE_PASSWORD=your_keystore_password
KEY_ALIAS=your_key_alias
KEY_PASSWORD=your_key_password
```

### Step 3: Add Firebase Configuration

Download `google-services.json` from Firebase Console and place it in:
```
app/google-services.json
```

### Step 4: Build

**Debug APK:**
```bash
./gradlew assembleDebug
```

**Release APK:**
```bash
./gradlew assembleRelease
```

**Run on device:**
```bash
./gradlew installDebug
```

---

## 📁 Project Structure

```
TelegramBotApp/
├── app/                              # Main app module
│   ├── src/main/
│   │   ├── java/com/telegrambot/
│   │   │   ├── di/                   # Dependency injection
│   │   │   ├── data/                 # Data layer
│   │   │   │   ├── local/            # Room database
│   │   │   │   ├── remote/           # Telegram API
│   │   │   │   └── repository/       # Repository implementations
│   │   │   ├── domain/               # Business logic
│   │   │   │   ├── model/            # Domain models
│   │   │   │   ├── repository/       # Repository interfaces
│   │   │   │   └── usecase/          # Use cases
│   │   │   ├── presentation/         # UI layer
│   │   │   │   ├── ui/               # Activities & Fragments
│   │   │   │   ├── viewmodel/        # ViewModels
│   │   │   │   ├── adapter/          # RecyclerView adapters
│   │   │   │   └── state/            # UI state models
│   │   │   ├── service/              # Background services
│   │   │   ├── worker/               # WorkManager workers
│   │   │   └── TelegramBotApp.kt    # Application class
│   │   ├── res/                      # Resources
│   │   │   ├── layout/               # XML layouts
│   │   │   ├── xml/                  # Configuration files
│   │   │   └── values/               # Strings, colors, dimens
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts              # App dependencies
│   └── proguard-rules.pro            # ProGuard rules
├── build.gradle.kts                  # Root Gradle config
├── settings.gradle.kts               # Module configuration
├── gradle.properties                 # Gradle properties
├── .github/
│   └── workflows/                    # CI/CD pipelines
├── scripts/
│   └── scan-repository.sh            # Repository scan
└── README.md
```

---

## 🔑 Key Components

### 1. Data Layer
- **Local Storage**: Room database with Message and User entities
- **Remote API**: Retrofit + OkHttp with Telegram Bot API integration
- **Repository**: Implementation of domain repository interface

### 2. Domain Layer
- **Models**: Message, User, BotConnectionState
- **Repositories**: Abstract interfaces
- **Use Cases**: GetMessages, SendMessage

### 3. Presentation Layer
- **MainActivity**: Entry point with Material Design theme
- **ChatFragment**: Message display and input
- **ChatViewModel**: MVVM state management
- **MessageAdapter**: RecyclerView for messages

### 4. Background Processing
- **BotCommunicationService**: Real-time bot sync
- **BotSyncWorker**: Periodic sync with WorkManager
- **UpdateService**: OTA update checks
- **FirebaseMessagingService**: Push notifications
- **BootReceiver**: Device boot handling

### 5. Dependency Injection
- **Hilt**: Application-level DI
- **Modules**: Database, Network, Repository

---

## 🧪 Testing

### Run Unit Tests
```bash
./gradlew test
```

### Run Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Code Coverage
```bash
./gradlew testDebugUnitTestCoverage
```

---

## 📊 CI/CD Pipeline

### GitHub Actions Workflows

**Android Build**: `android-build.yml`
- Builds APK and AAB
- Runs on push to main/develop
- Uploads artifacts

**Code Quality**: `code-quality.yml`
- Runs lint checks
- Executes unit tests
- Generates reports

---

## 🔐 Security

### Network Security
- SSL/TLS enabled for all API calls
- Certificate pinning ready
- Network security config in place

### Data Security
- SharedPreferences encrypted with EncryptedSharedPreferences
- Database encryption ready
- Biometric authentication support

### Code Security
- ProGuard obfuscation enabled
- Sensitive code protected
- API keys in build config

---

## 🚀 Deployment

### To Play Store
```bash
./gradlew bundleRelease
```

Upload `app/build/outputs/bundle/release/app-release.aab` to Google Play Console

### Direct APK Distribution
```bash
./gradlew assembleRelease
```

APK located at: `app/build/outputs/apk/release/app-release.apk`

### Firebase Distribution
Configure in `android-build.yml` for automated distribution

---

## 📱 Features

### Telegram Bot Integration
- Real-time message communication
- Automatic reconnection with backoff
- Message queuing for offline scenarios
- Full Telegram Bot API support

### Intelligent Updates
- Background update checking
- Delta update capability
- Automatic installation
- Rollback on crash

### Offline-First Sync
- Local message persistence
- Automatic sync when online
- Conflict resolution
- Message batching

### Push Notifications
- Firebase Cloud Messaging
- Real-time notifications
- Data message handling

### Security
- Network security hardened
- Biometric authentication
- Encrypted storage
- Code obfuscation

---

## 🐛 Troubleshooting

### Build Errors
```bash
# Clean and rebuild
./gradlew clean build

# Check dependencies
./gradlew dependencies
```

### Runtime Errors
- Check logs: `adb logcat`
- Enable debug logging in BuildConfig
- Use Android Studio debugger

### Network Issues
- Verify API token in local.properties
- Check internet connectivity
- Review network security config

---

## 📚 Additional Resources

- [Android Architecture Guide](https://developer.android.com/topic/architecture)
- [Telegram Bot API Docs](https://core.telegram.org/bots/api)
- [Hilt Documentation](https://dagger.dev/hilt)
- [Room Database Guide](https://developer.android.com/training/data-storage/room)

---

## 📞 Support

For issues or questions:
1. Check the troubleshooting section
2. Review GitHub Issues
3. Check logs for error details

---

**Last Updated**: 2026-06-05  
**Version**: 1.0.0  
**Status**: Production Ready ✅
