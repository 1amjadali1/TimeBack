package com.timeback.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timeback.app.data.PreferencesManager
import com.timeback.app.ui.theme.BgMidnight
import com.timeback.app.ui.theme.CreamAccent
import com.timeback.app.ui.theme.InkDark
import com.timeback.app.ui.theme.LineBorder
import com.timeback.app.ui.theme.SurfaceDark
import com.timeback.app.ui.theme.TextMuted
import com.timeback.app.ui.theme.TextPrimary
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun BlockerSettingsScreen(prefsManager: PreferencesManager) {
    val dailyLimit by prefsManager.dailyLimitFlow.collectAsState(initial = 50)
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgMidnight)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "TimeBack Blocker & Limits",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextPrimary
        )
        Text(
            text = "Enforce limits when compulsive scrolling kicks in.",
            fontSize = 12.sp,
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, LineBorder, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Daily Limit",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "$dailyLimit Reels",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = CreamAccent
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Slider(
                    value = dailyLimit.toFloat(),
                    onValueChange = { newVal ->
                        scope.launch { prefsManager.setDailyLimit(newVal.roundToInt()) }
                    },
                    valueRange = 10f..200f,
                    steps = 18,
                    colors = SliderDefaults.colors(
                        thumbColor = CreamAccent,
                        activeTrackColor = CreamAccent,
                        inactiveTrackColor = LineBorder
                    )
                )

                Text(
                    text = "Once you reach $dailyLimit reels across Instagram, YouTube, etc., TimeBack will block the reels player.",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, LineBorder, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Monitored Platforms",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(14.dp))

                AppToggleRow("Instagram Reels", true)
                AppToggleRow("YouTube Shorts", true)
                AppToggleRow("Facebook Reels", true)
                AppToggleRow("Snapchat Spotlight", true)
            }
        }
    }
}

@Composable
fun AppToggleRow(name: String, initialChecked: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = name, fontSize = 14.sp, color = TextPrimary)
        Switch(
            checked = initialChecked,
            onCheckedChange = {},
            colors = SwitchDefaults.colors(
                checkedThumbColor = InkDark,
                checkedTrackColor = CreamAccent
            )
        )
    }
}
