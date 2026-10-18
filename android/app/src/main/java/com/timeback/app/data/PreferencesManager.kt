package com.timeback.app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "timeback_prefs")

class PreferencesManager(private val context: Context) {

    companion object {
        val KEY_ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val KEY_USER_ROLE = stringPreferencesKey("user_role")
        val KEY_SCROLL_PLATFORMS = stringSetPreferencesKey("scroll_platforms")
        val KEY_TIME_REASON = stringSetPreferencesKey("time_reason")
        val KEY_FEELINGS = stringSetPreferencesKey("feelings")
        val KEY_SCROLL_TIME = stringPreferencesKey("scroll_time")

        val KEY_USER_NAME = stringPreferencesKey("user_name")
        val KEY_USER_EMAIL = stringPreferencesKey("user_email")

        val KEY_TODAY_DATE = stringPreferencesKey("today_date")
        val KEY_TOTAL_REELS = intPreferencesKey("total_reels")
        val KEY_IG_REELS = intPreferencesKey("ig_reels")
        val KEY_YT_SHORTS = intPreferencesKey("yt_shorts")
        val KEY_FB_REELS = intPreferencesKey("fb_reels")
        val KEY_SC_REELS = intPreferencesKey("sc_reels")

        val KEY_DAILY_LIMIT = intPreferencesKey("daily_limit")
        val KEY_STRICT_MODE = booleanPreferencesKey("strict_mode")
        val KEY_IS_PRO_USER = booleanPreferencesKey("is_pro_user")
        val KEY_SUBSCRIPTION_PLAN = stringPreferencesKey("subscription_plan")
        val KEY_SUBSCRIPTION_EXPIRY = stringPreferencesKey("subscription_expiry")
        val KEY_OVERLAY_ENABLED = booleanPreferencesKey("overlay_enabled")
    }

    private fun getTodayFormatted(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    val isOnboardingCompletedFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_ONBOARDING_COMPLETED] ?: false
    }

    val totalReelsFlow: Flow<Int> = context.dataStore.data.map { prefs ->
        val savedDate = prefs[KEY_TODAY_DATE] ?: ""
        if (savedDate == getTodayFormatted()) prefs[KEY_TOTAL_REELS] ?: 0 else 0
    }

    val dailyLimitFlow: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[KEY_DAILY_LIMIT] ?: 50
    }

    val isProUserFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_IS_PRO_USER] ?: false
    }

    val subscriptionPlanFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_SUBSCRIPTION_PLAN] ?: "Free Plan"
    }

    val userEmailFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_USER_EMAIL] ?: "user@timeback.app"
    }

    val overlayEnabledFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_OVERLAY_ENABLED] ?: true
    }

    suspend fun saveOnboardingAnswers(
        role: String,
        platforms: Set<String>,
        timeReasons: Set<String>,
        feelings: Set<String>,
        scrollTime: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[KEY_ONBOARDING_COMPLETED] = true
            prefs[KEY_USER_ROLE] = role
            prefs[KEY_SCROLL_PLATFORMS] = platforms
            prefs[KEY_TIME_REASON] = timeReasons
            prefs[KEY_FEELINGS] = feelings
            prefs[KEY_SCROLL_TIME] = scrollTime
        }
    }

    suspend fun incrementReelCount(packageName: String): Int {
        var newTotal = 0
        context.dataStore.edit { prefs ->
            val today = getTodayFormatted()
            val savedDate = prefs[KEY_TODAY_DATE] ?: ""

            if (savedDate != today) {
                prefs[KEY_TODAY_DATE] = today
                prefs[KEY_TOTAL_REELS] = 0
                prefs[KEY_IG_REELS] = 0
                prefs[KEY_YT_SHORTS] = 0
                prefs[KEY_FB_REELS] = 0
                prefs[KEY_SC_REELS] = 0
            }

            val currentTotal = prefs[KEY_TOTAL_REELS] ?: 0
            newTotal = currentTotal + 1
            prefs[KEY_TOTAL_REELS] = newTotal

            when {
                packageName.contains("instagram") -> {
                    val count = prefs[KEY_IG_REELS] ?: 0
                    prefs[KEY_IG_REELS] = count + 1
                }
                packageName.contains("youtube") -> {
                    val count = prefs[KEY_YT_SHORTS] ?: 0
                    prefs[KEY_YT_SHORTS] = count + 1
                }
                packageName.contains("facebook") -> {
                    val count = prefs[KEY_FB_REELS] ?: 0
                    prefs[KEY_FB_REELS] = count + 1
                }
                packageName.contains("snapchat") -> {
                    val count = prefs[KEY_SC_REELS] ?: 0
                    prefs[KEY_SC_REELS] = count + 1
                }
            }
        }
        return newTotal
    }

    suspend fun setDailyLimit(limit: Int) {
        context.dataStore.edit { it[KEY_DAILY_LIMIT] = limit }
    }

    suspend fun setProUser(isPro: Boolean, plan: String = "TimeBack Plus") {
        context.dataStore.edit {
            it[KEY_IS_PRO_USER] = isPro
            it[KEY_SUBSCRIPTION_PLAN] = plan
        }
    }

    suspend fun setUserProfile(name: String, email: String) {
        context.dataStore.edit {
            it[KEY_USER_NAME] = name
            it[KEY_USER_EMAIL] = email
        }
    }

    suspend fun setOverlayEnabled(enabled: Boolean) {
        context.dataStore.edit { it[KEY_OVERLAY_ENABLED] = enabled }
    }
}
