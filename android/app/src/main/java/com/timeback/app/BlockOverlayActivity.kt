package com.timeback.app

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class BlockOverlayActivity : ComponentActivity() {

    companion object {
        const val EXTRA_APP_NAME = "extra_app_name"
        const val EXTRA_COUNT = "extra_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appPackage = intent.getStringExtra(EXTRA_APP_NAME) ?: "Social App"
        val count = intent.getIntExtra(EXTRA_COUNT, 50)

        triggerVibration()

        setContent {
            BlockOverlayScreen(
                appName = formatAppName(appPackage),
                count = count,
                onExit = {
                    val homeIntent = Intent(Intent.ACTION_MAIN).apply {
                        addCategory(Intent.CATEGORY_HOME)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(homeIntent)
                    finish()
                }
            )
        }
    }

    private fun formatAppName(pkg: String): String {
        return when {
            pkg.contains("instagram") -> "Instagram Reels"
            pkg.contains("youtube") -> "YouTube Shorts"
            pkg.contains("facebook") -> "Facebook Reels"
            pkg.contains("snapchat") -> "Snapchat Spotlight"
            else -> "Reels Feed"
        }
    }

    private fun triggerVibration() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(300)
        }
    }
}

@Composable
fun BlockOverlayScreen(
    appName: String,
    count: Int,
    onExit: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xE60A0907)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .padding(24.dp),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF14130F))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🛑",
                    fontSize = 52.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "TimeBack Daily Limit Hit!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFC4A52),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "You reached your limit on $appName today ($count reels). TimeBack has stepped in to save your focus.",
                    fontSize = 14.sp,
                    color = Color(0xCCFCFFFA),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onExit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE9B4))
                ) {
                    Text(
                        text = "Take Time Back (Exit)",
                        color = Color(0xFF2B1600),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
