package com.timeback.app.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.timeback.app.BlockOverlayActivity
import com.timeback.app.data.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ReelDetectionService : AccessibilityService() {

    private val tag = "ReelDetectionService"
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private lateinit var prefsManager: PreferencesManager

    private var lastScrollTimestamp = 0L
    private val scrollDebounceMs = 700L

    override fun onServiceConnected() {
        super.onServiceConnected()
        prefsManager = PreferencesManager(applicationContext)
        Log.d(tag, "TimeBack ReelDetectionService connected.")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        val pkgName = event.packageName?.toString() ?: return

        when (event.eventType) {
            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                handleScrollEvent(pkgName, event)
            }
        }
    }

    private fun handleScrollEvent(pkgName: String, event: AccessibilityEvent) {
        val now = SystemClock.uptimeMillis()
        if (now - lastScrollTimestamp < scrollDebounceMs) {
            return
        }

        if (isReelsView(pkgName, event)) {
            lastScrollTimestamp = now

            serviceScope.launch {
                val newCount = prefsManager.incrementReelCount(pkgName)
                val dailyLimit = prefsManager.dailyLimitFlow.first()

                Log.d(tag, "TimeBack Counted! App: $pkgName | Today's Count: $newCount | Limit: $dailyLimit")

                val updateIntent = Intent(FloatingOverlayService.ACTION_UPDATE_COUNT).apply {
                    putExtra(FloatingOverlayService.EXTRA_COUNT, newCount)
                    setPackage(packageName)
                }
                sendBroadcast(updateIntent)

                if (newCount >= dailyLimit) {
                    triggerBlocker(pkgName, newCount)
                }
            }
        }
    }

    private fun isReelsView(pkgName: String, event: AccessibilityEvent): Boolean {
        val source = event.source ?: return false

        return when {
            pkgName.contains("instagram") -> {
                containsNodeWithTextOrId(source, listOf("clips", "reel", "reel_viewer", "like_button", "comment_button"))
            }
            pkgName.contains("youtube") -> {
                containsNodeWithTextOrId(source, listOf("shorts_container", "reel_player_page_holder", "reel_recycler"))
            }
            pkgName.contains("facebook") -> {
                containsNodeWithTextOrId(source, listOf("reel", "fb_shorts", "short_form_video"))
            }
            pkgName.contains("snapchat") -> {
                containsNodeWithTextOrId(source, listOf("spotlight", "vertical_pager"))
            }
            else -> false
        }
    }

    private fun containsNodeWithTextOrId(node: AccessibilityNodeInfo, keywords: List<String>): Boolean {
        val viewId = node.viewIdResourceName?.lowercase() ?: ""
        val text = node.text?.toString()?.lowercase() ?: ""
        val desc = node.contentDescription?.toString()?.lowercase() ?: ""

        for (k in keywords) {
            if (viewId.contains(k) || text.contains(k) || desc.contains(k)) {
                return true
            }
        }

        for (i in 0 until minOf(node.childCount, 8)) {
            val child = node.getChild(i)
            if (child != null) {
                val childId = child.viewIdResourceName?.lowercase() ?: ""
                val childDesc = child.contentDescription?.toString()?.lowercase() ?: ""
                for (k in keywords) {
                    if (childId.contains(k) || childDesc.contains(k)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun triggerBlocker(pkgName: String, currentCount: Int) {
        Log.w(tag, "Limit reached ($currentCount). Triggering TimeBack blocker.")
        performGlobalAction(GLOBAL_ACTION_BACK)

        val blockIntent = Intent(applicationContext, BlockOverlayActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(BlockOverlayActivity.EXTRA_APP_NAME, pkgName)
            putExtra(BlockOverlayActivity.EXTRA_COUNT, currentCount)
        }
        startActivity(blockIntent)
    }

    override fun onInterrupt() {
        Log.d(tag, "TimeBack ReelDetectionService interrupted.")
    }
}
