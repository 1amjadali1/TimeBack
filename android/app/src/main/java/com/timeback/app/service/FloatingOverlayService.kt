package com.timeback.app.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.timeback.app.MainActivity
import com.timeback.app.R

class FloatingOverlayService : Service() {

    companion object {
        const val ACTION_UPDATE_COUNT = "com.timeback.app.ACTION_UPDATE_COUNT"
        const val EXTRA_COUNT = "extra_count"
        private const val NOTIFICATION_CHANNEL_ID = "timeback_overlay_channel"
        private const val NOTIFICATION_ID = 2001
    }

    private lateinit var windowManager: WindowManager
    private var floatingView: LinearLayout? = null
    private var countTextView: TextView? = null
    private var mascotTextView: TextView? = null

    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f

    private val countReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ACTION_UPDATE_COUNT) {
                val count = intent.getIntExtra(EXTRA_COUNT, 0)
                updateBubble(count)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        createFloatingBubble()

        val filter = IntentFilter(ACTION_UPDATE_COUNT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(countReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(countReceiver, filter)
        }
    }

    private fun startForegroundService() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)

        val notification: Notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("TimeBack Active")
            .setContentText("Reel counter tracking session")
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun createFloatingBubble() {
        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 40
            y = 200
        }

        val pill = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(28, 16, 28, 16)
            gravity = Gravity.CENTER_VERTICAL
            background = GradientDrawable().apply {
                setColor(Color.parseColor("#E60A0907"))
                cornerRadius = 999f
                setStroke(4, Color.parseColor("#FFE9B4"))
            }
        }

        mascotTextView = TextView(this).apply {
            text = "🧠"
            textSize = 16f
            setPadding(0, 0, 14, 0)
        }
        pill.addView(mascotTextView)

        countTextView = TextView(this).apply {
            text = "0"
            setTextColor(Color.parseColor("#FFE9B4"))
            textSize = 15f
            typeface = Typeface.DEFAULT_BOLD
        }
        pill.addView(countTextView)

        pill.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    params.x = initialX + (event.rawX - initialTouchX).toInt()
                    params.y = initialY + (event.rawY - initialTouchY).toInt()
                    windowManager.updateViewLayout(pill, params)
                    true
                }
                MotionEvent.ACTION_UP -> {
                    val diffX = Math.abs(event.rawX - initialTouchX)
                    val diffY = Math.abs(event.rawY - initialTouchY)
                    if (diffX < 10 && diffY < 10) {
                        val appIntent = Intent(this, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(appIntent)
                    }
                    true
                }
                else -> false
            }
        }

        floatingView = pill
        windowManager.addView(floatingView, params)
    }

    private fun updateBubble(count: Int) {
        countTextView?.text = count.toString()
        mascotTextView?.text = when {
            count < 50 -> "🧠"
            count < 100 -> "😐"
            count < 150 -> "😰"
            count < 200 -> "😵"
            else -> "💀"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(countReceiver)
        if (floatingView != null) {
            windowManager.removeView(floatingView)
        }
    }
}
