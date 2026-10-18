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
│   │   │   └── java\com\timeback\app\
│   │   │       ├── MainActivity.kt
│   │   │       ├── BlockOverlayActivity.kt
│   │   │       ├── service\
│   │   │       │   ├── ReelDetectionService.kt
│   │   │       │   ├── FloatingOverlayService.kt
│   │   │       │   └── BootReceiver.kt
│   │   │       ├── data\
│   │   │       │   ├── PreferencesManager.kt
│   │   │       │   └── BillingManager.kt
│   │   │       └── ui\
│   │   │           ├── screens\ (Onboarding, Permissions, Home, Blocker, Battle, Paywall, Profile)
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

## 2. Onboarding & Permissions Flow

1. **5-Step Onboarding Questionnaire**:
   * Q1: What do you do? (School student, College student, Working, Other)
   * Q2: Where do you scroll the most? (Instagram Reels, YouTube Shorts, Snapchat, Facebook, Moj/Josh/ShareChat)
   * Q3: What do you want that time back for? (Studies, Work, Sleep, Fitness, Family, Peace of mind)
   * Q4: After a long scroll, how do you feel? (Guilty, Tired, Can't focus, I sleep late)
   * Q5: When do you scroll the most? (Morning in bed, During study/work, After dinner, Late at night)
2. **Permissions Setup**:
   * **Reel Counting**: AccessibilityService API
   * **Floating Bubble**: Display over other apps (`SYSTEM_ALERT_WINDOW`)
   * **Invisible Background Running**: Exempt from battery optimization & starts on device boot via `BootReceiver`.

---

## 3. Subscription & Pricing Setup in Google Play Console

In **Google Play Console -> Monetize -> Subscriptions**:

1. Subscription Product ID: `timeback_plus`
2. **Base Plan 1 (Monthly):**
   * ID: `monthly-plan`
   * Regular Price: **₹49 / month**
   * Promotional Offer: **₹25 first month** (Introductory price)
3. **Base Plan 2 (Annual):**
   * ID: `yearly-plan`
   * Regular Price: **₹449 / year**
   * Promotional Offer: **₹299 / year** (Save ₹150 discount offer)

---

## 4. How to Pass Google Play Accessibility Policy Review (Crucial!)

When filling out the **Policy -> App Content -> Accessibility Tool** declaration in Google Play Console:

1. **Does your app qualify as an Accessibility tool?**
   * Select: **No, but it uses the API for a valid user-facing functionality (Digital Wellbeing / Focus & Habit Reduction).**
2. **What specific user-facing feature uses this API?**
   * *Copy & paste this exact wording:*
   > "TimeBack uses the Android AccessibilityService API strictly to detect scroll events on vertical short-form video viewports (Instagram Reels, YouTube Shorts, Facebook Reels, Snapchat Spotlight) in order to display a real-time counter and enforce user-configured daily digital wellbeing limits. When a user exceeds their daily quota, the service navigates back from the video screen to prevent compulsive doomscrolling."
3. **Does the app access or collect personal data?**
   * Select: **No. It does not access, read, transmit, or store private personal messages, keystrokes, user accounts, or video content.**
4. **Is there an in-app prominent disclosure?**
   * Select: **Yes. The app displays a prominent modal disclosure explaining the exact usage before asking the user to navigate to Accessibility Settings.** *(Implemented in `PermissionDisclosureDialog.kt` and `PermissionsSetupScreen.kt`).*
