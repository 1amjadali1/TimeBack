# TimeBack - Google Play Store Listing & Submission Metadata

> **Important**: This document contains the exact text and answers to copy-paste directly into your **Google Play Console**.

---

## 1. App Details & Store Listing Copy

### App Title (Max 30 characters):
```text
TimeBack: Reels & Shorts Block
```
*(Alternate option: `TimeBack: Reel Counter Blocker`)*

### Short Description (Max 80 characters):
```text
See your reel count, stop doomscrolling, and get your time back.
```
*(Length: 64 characters - Clean & punchy)*

### Full Description (Max 4000 characters):
```text
Do you open Instagram or YouTube for "just 5 minutes" and suddenly lose 2 hours doomscrolling?

TimeBack is your personal digital wellbeing guard that counts every short-form video you scroll in real time, visualizes what it does to your focus, and automatically blocks reels when you hit your daily limit.

━━━━━━━━━━━━━━━━━━━━━
✨ WHY TIMEBACK WORKS
━━━━━━━━━━━━━━━━━━━━━

1. ⚡ LIVE REEL & SHORT COUNTER
TimeBack counts every reel and short you scroll across Instagram, YouTube Shorts, Snapchat, Facebook, and local short-form platforms. A floating, draggable on-screen bubble shows your live session count so you always stay aware.

2. 🧠 THE DEGRADING MASCOT
Every reel drains your focus. TimeBack visualizes this with a live mascot that starts fresh and pink, then gets tired, strained, and visibly degraded as your scroll count climbs past 100 and 200 reels. It provides instant psychological friction to help you close the feed.

3. 🛑 AUTOMATIC REELS BLOCKER (TIMEBACK PLUS)
Set a custom daily quota (e.g., 30 reels or 50 reels). The moment you reach your limit, TimeBack safely intercepts the reels viewport and brings you back to your home screen—without blocking regular DMs, stories, or long-form videos!

4. 🥊 1v1 BATTLE RING WITH FRIENDS
Compete against your friends or family to see who scrolls fewer reels each day. Lower scroll count wins the daily trophy! Mutual accountability makes breaking the habit fun and engaging.

5. ⚡ INVISIBLE & BATTERY-EFFICIENT
Designed with modern Android architecture. TimeBack runs quietly in the background without draining battery or slowing down your device.

━━━━━━━━━━━━━━━━━━━━━
🔒 PRIVACY & ACCESSIBILITY DISCLOSURE
━━━━━━━━━━━━━━━━━━━━━
TimeBack uses Android's AccessibilityService API strictly to detect scroll events on vertical short-form video viewports (Instagram Reels, YouTube Shorts, Facebook Reels, Snapchat Spotlight) in order to calculate your real-time reel counter and enforce your self-selected daily limit.

• We DO NOT read private messages, chats, or emails.
• We DO NOT record keystrokes, passwords, or personal credentials.
• We DO NOT store or transmit personal account data to external servers.
• All usage counters are stored locally on your device.

Take your focus, sleep, and time back. Install TimeBack today!
```

---

## 2. Categorization & Store Settings

* **App Category**: `Productivity` or `Health & Fitness`
* **Tags**:
  * Productivity
  * Screen Time
  * Self-Regulation
  * Habit Tracker
  * Digital Wellbeing
* **Target Audience**: `Ages 13 and up` (Select 13-15, 16-17, 18+)
* **Does your app target children?**: Select **No**.

---

## 3. Data Safety Form Answers (Mandatory in Play Console)

When Google Play asks the Data Safety questions:

1. **Does your app collect or share any of the required user data types?**
   * Select: **No** *(TimeBack stores reel counts locally on device via DataStore and does not collect or transmit personal user data to external tracking servers).*
2. **Is all user data collected by your app encrypted in transit?**
   * Select: **Yes** *(All billing communication with Google Play is encrypted via HTTPS/TLS).*
3. **Do you provide a way for users to request that their data be deleted?**
   * Select: **Yes** *(Users can clear all local counts inside the app or email support@timeback.app).*

---

## 4. Policy Declaration: Accessibility Tool Form (Crucial!)

Under **Policy ➔ App Content ➔ Accessibility Services**:

1. **Does your app qualify as an Accessibility tool?**
   * Select: **No, but it uses the API for a valid user-facing functionality (Digital Wellbeing / Focus & Habit Reduction).**
2. **Describe the specific user-facing feature that uses this API:**
   * *Copy & paste this exact text:*
   > "TimeBack uses the Android AccessibilityService API strictly to detect scroll events on vertical short-form video viewports (Instagram Reels, YouTube Shorts, Facebook Reels, Snapchat Spotlight) in order to display a real-time counter and enforce user-configured daily digital wellbeing limits. When a user exceeds their daily quota, the service navigates back from the video screen to prevent compulsive doomscrolling."
3. **Does the app access or collect personal data?**
   * Select: **No. It does not access, read, transmit, or store private personal messages, keystrokes, user accounts, or video content.**
4. **Is there an in-app prominent disclosure?**
   * Select: **Yes. The app displays a prominent modal disclosure explaining the exact usage before asking the user to navigate to Accessibility Settings.**
5. **Video Demonstration URL:**
   * Google requires a 30-second YouTube unlisted video showing:
     1. Opening the app and seeing the permission disclosure.
     2. Turning on the accessibility service in Android Settings.
     3. Opening Instagram or YouTube and watching the live counter bubble increment when scrolling.

---

## 5. Google Play Closed Testing (20 Testers Rule)

For newly registered personal Google Play Developer accounts:
1. Google requires your app to be published on the **Closed Testing Track** with at least **20 opted-in testers for 14 continuous days** before production access is granted.
2. **How to set this up quickly**:
   * In Play Console, create a Closed Testing track.
   * Create an Email List with 20 Gmail IDs (friends, family, or colleagues).
   * Share the opt-in link provided by Google Play.
   * Ask each tester to download the app and open it once.
   * After 14 days, the **"Apply for Production"** button activates automatically!
