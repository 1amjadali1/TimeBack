# TimeBack ⏳🧠

> **See your reel count. Take your time back.**

TimeBack is a modern Android digital wellbeing and anti-doomscrolling application designed to break the short-form video addiction loop across Instagram Reels, YouTube Shorts, Facebook Reels, and Snapchat Spotlight.

---

## ✨ Features

- **⚡ Real-Time Reel Counter**: Counts every reel scrolled in real-time using high-efficiency Android Accessibility Services with 700ms gesture debouncing.
- **🧠 Dynamic Mascot Brain**: Live visual mascot that degrades through 5 psychological stages (Healthy ➔ Warming ➔ Strained ➔ Droopy ➔ Brain Rot) as your reel count climbs.
- **🎈 Floating Draggable Bubble**: A floating, draggable pill overlay showing your real-time count and expressive mini mascot while using third-party apps.
- **🛑 Hard Limit Blocker**: Automatically exits the reels viewport and displays a calming modal when your daily quota is reached.
- **🥊 1v1 Battle Ring**: Challenge friends to daily scroll battles—lowest reel count wins the daily trophy.
- **💳 TimeBack Plus**: Premium subscriptions via Google Play Billing (Monthly ₹99 / Annual ₹999).

---

## 🏗️ Architecture & Tech Stack

- **Platform**: Android Native (Kotlin 2.0, Min SDK 26, Target SDK 35)
- **UI Toolkit**: Jetpack Compose & Material 3
- **Services**:
  - `ReelDetectionService`: AccessibilityService monitoring Instagram, YouTube Shorts, FB, and Snapchat.
  - `FloatingOverlayService`: System alert window draggable floating view.
- **State & Storage**: Jetpack DataStore Preferences
- **Monetization**: Google Play In-App Billing Library v7 (`BillingManager`)

---

## 🚀 Quick Start

### 1. Test the Live Interactive Simulator
Open `preview/index.html` in your web browser:
```bash
# On Windows PowerShell
Start-Process "d:\My Career\Reels App\preview\index.html"
```

### 2. Build in Android Studio
1. Open Android Studio.
2. Select **File ➔ Open** and point to `d:\My Career\Reels App\android`.
3. Let Gradle sync and run on your physical Android device or emulator.

---

## 📄 License & Policies
- [Privacy Policy](docs/PRIVACY_POLICY.html)
- [Terms of Service](docs/TERMS_OF_SERVICE.html)
- [Google Play Launch Guide](docs/GOOGLE_PLAY_LAUNCH_GUIDE.md)
