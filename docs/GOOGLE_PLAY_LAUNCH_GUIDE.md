# TimeBack - Complete Google Play Store Launch Guide

This guide gives you everything you need to compile, test, configure payments, pass Google Play review, and launch **TimeBack** on the Google Play Store.

---

## 1. Project Overview & Folder Structure

All code is organized inside `D:\My Career\Reels App\`:

```
d:\My Career\Reels App\
├── preview\
│   └── index.html                <-- Double-click to run live simulator in Chrome/Edge!
├── android\
│   ├── app\
│   │   ├── src\main\
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── res\
│   │   │   │   ├── xml\accessibility_service_config.xml
│   │   │   │   └── values\ (strings, colors, themes)
│   │   │   └── java\com.timeback.app\
│   │   │       ├── MainActivity.kt
│   │   │       ├── BlockOverlayActivity.kt
│   │   │       ├── service\
│   │   │       │   ├── ReelDetectionService.kt
│   │   │       │   └── FloatingOverlayService.kt
│   │   │       ├── data\
│   │   │       │   └── PreferencesManager.kt
│   │   │       └── ui\
│   │   │           ├── screens\ (Home, Blocker, Battle, Paywall)
│   │   │           └── components\ (MascotBrainView)
│   │   └── build.gradle.kts
│   ├── build.gradle.kts
│   └── settings.gradle.kts
└── docs\
    ├── GOOGLE_PLAY_LAUNCH_GUIDE.md
    ├── PRIVACY_POLICY.html
    └── TERMS_OF_SERVICE.html
```

---

## 2. How to Open and Run in Android Studio

1. **Install Android Studio** (Ladybug / Iguana or later) with Android SDK 35 and JDK 17.
2. In Android Studio, click **File -> Open...** and select:
   `D:\My Career\Reels App\android`
3. Allow Gradle to sync dependencies automatically.
4. Connect an Android phone (with USB Debugging enabled) or start an Android Emulator.
5. Click the green **Run (▶)** button.

---

## 3. How to Pass Google Play Accessibility Policy Review (Crucial!)

Google has strict scrutiny over apps that use `android.accessibilityservice.AccessibilityService`. If you do not answer their declaration form correctly, the app will be rejected.

### The Required Google Play Declaration Form Answers:

When filling out the **Policy -> App Content -> Accessibility Tool** declaration in Google Play Console:

1. **Does your app qualify as an Accessibility tool?**
   * Select: **No, but it uses the API for a valid user-facing functionality (Digital Wellbeing / Focus & Habit Reduction).**
2. **What specific user-facing feature uses this API?**
   * *Copy & paste this exact wording:*
   > "TimeBack uses the Android AccessibilityService API strictly to detect scroll events on vertical short-form video viewports (Instagram Reels, YouTube Shorts, Facebook Reels, Snapchat Spotlight) in order to display a real-time counter and enforce user-configured daily digital wellbeing limits. When a user exceeds their daily quota, the service navigates back from the video screen to prevent compulsive doomscrolling."
3. **Does the app access or collect personal data?**
   * Select: **No. It does not access, read, transmit, or store private personal messages, keystrokes, user accounts, or video content.**
4. **Is there an in-app prominent disclosure?**
   * Select: **Yes. The app displays a prominent modal disclosure explaining the exact usage before asking the user to navigate to Accessibility Settings.** *(This is implemented in `PermissionDisclosureDialog.kt`).*
5. **Video Demonstration URL:**
   * Google will ask for a short YouTube video (Unlisted) showing the user opening the app, seeing the disclosure, turning on Accessibility, and then scrolling a reel to see the counter increment.

---

## 4. Google Play Billing Setup (Subscriptions)

In **Google Play Console -> Monetize -> Subscriptions**:

1. Create a Subscription base product:
   * **Product ID:** `timeback_plus`
2. Add Base Plans:
   * **Base Plan 1 (Monthly):**
     * ID: `timeback_plus_monthly`
     * Price: ₹99 / month (or equivalent in other currencies)
     * Billing period: 1 month
   * **Base Plan 2 (Yearly):**
     * ID: `timeback_plus_yearly`
     * Price: ₹999 / year
     * Free trial offer: 7 days free
3. Once created, Google Play automatically validates purchases inside `PaywallScreen.kt`.

---

## 5. Generating Release APK / Android App Bundle (.aab)

To upload to Google Play:
1. In Android Studio, go to **Build -> Generate Signed Bundle / APK...**
2. Choose **Android App Bundle (.aab)**.
3. Create a new Keystore:
   * Store path: `timeback-release-key.jks`
   * Password: *(Create a secure password)*
   * Alias: `timeback_key`
4. Select **Release** build type.
5. Upload the resulting `.aab` file to **Google Play Console -> Production / Closed Testing**.

---

## 6. Privacy Policy & Terms Links

Google Play requires public URLs for your Privacy Policy and Terms of Use.
You can host the two files inside `docs/` (`PRIVACY_POLICY.html` and `TERMS_OF_SERVICE.html`) on GitHub Pages or Vercel for free!
