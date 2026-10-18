package com.timeback.app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "timeback_prefs")

class PreferencesManager(private val context: Context) {

    companion object {
        val KEY_TODAY_DATE = stringPreferencesKey("today_date")
        val KEY_TOTAL_REELS = intPreferencesKey("total_reels")
        val KEY_IG_REELS = intPreferencesKey("ig_reels")
        val KEY_YT_SHORTS = intPreferencesKey("yt_shorts")
        val KEY_FB_REELS = intPreferencesKey("fb_reels")
        val KEY_SC_REELS = intPreferencesKey("sc_reels")

        val KEY_DAILY_LIMIT = intPreferencesKey("daily_limit")
        val KEY_STRICT_MODE = booleanPreferencesKey("strict_mode")
        val KEY_IS_PRO_USER = booleanPreferencesKey("is_pro_user")
        val KEY_OVERLAY_ENABLED = booleanPreferencesKey("overlay_enabled")
    }

    private fun getTodayFormatted(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
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

    val overlayEnabledFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_OVERLAY_ENABLED] ?: true
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

    suspend fun setProUser(isPro: Boolean) {
        context.dataStore.edit { it[KEY_IS_PRO_USER] = isPro }
    }

    suspend fun setOverlayEnabled(enabled: Boolean) {
        context.dataStore.edit { it[KEY_OVERLAY_ENABLED] = enabled }
    }
}
