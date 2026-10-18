package com.timeback.app.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timeback.app.data.PreferencesManager
import com.timeback.app.service.FloatingOverlayService
import com.timeback.app.ui.components.MascotBrainView
import com.timeback.app.ui.theme.ApricotAccent
import com.timeback.app.ui.theme.BgMidnight
import com.timeback.app.ui.theme.CreamAccent
import com.timeback.app.ui.theme.FreshGreen
import com.timeback.app.ui.theme.InkDark
import com.timeback.app.ui.theme.LineBorder
import com.timeback.app.ui.theme.RotRed
import com.timeback.app.ui.theme.SurfaceDark
import com.timeback.app.ui.theme.SurfaceElevated
import com.timeback.app.ui.theme.TextMuted
import com.timeback.app.ui.theme.TextPrimary
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    context: Context,
    prefsManager: PreferencesManager
) {
    val totalCount by prefsManager.totalReelsFlow.collectAsState(initial = 0)
    val overlayEnabled by prefsManager.overlayEnabledFlow.collectAsState(initial = true)
    val scope = rememberCoroutineScope()
    var showDisclosure by remember { mutableStateOf(false) }

    if (showDisclosure) {
        PermissionDisclosureDialog(
            context = context,
            onDismiss = { showDisclosure = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgMidnight)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "TimeBack",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )
                Text(
                    text = "See your reel count. Take your time back.",
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(CreamAccent)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "LIVE",
                    color = InkDark,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, LineBorder, RoundedCornerShape(28.dp)),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MascotBrainView(count = totalCount, modifier = Modifier.size(140.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "$totalCount",
                    fontSize = 56.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary,
                    lineHeight = 56.sp
                )

                Text(
                    text = "REELS SCROLLED TODAY",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                val badgeText = when {
                    totalCount < 50 -> "Healthy Mind (Stage 1)"
                    totalCount < 100 -> "Brain Tiring (Stage 2)"
                    totalCount < 150 -> "Heavy Strain (Stage 3)"
                    totalCount < 200 -> "Droopy Brain (Stage 4)"
                    else -> "Brain Rot State (Stage 5)"
                }
                val badgeColor = when {
                    totalCount < 50 -> FreshGreen
                    totalCount < 100 -> CreamAccent
                    totalCount < 150 -> ApricotAccent
                    else -> RotRed
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(badgeColor.copy(alpha = 0.15f))
                        .border(1.dp, badgeColor.copy(alpha = 0.3f), RoundedCornerShape(999.dp))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = badgeText,
                        color = badgeColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, LineBorder, RoundedCornerShape(18.dp)),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceElevated)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Accessibility Service",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Required to detect & count reels live",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }

                Button(
                    onClick = { showDisclosure = true },
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CreamAccent)
                ) {
                    Text(text = "Setup", color = InkDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, LineBorder, RoundedCornerShape(18.dp)),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceElevated)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Floating Reel Counter",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Draggable live counter over other apps",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }

                Switch(
                    checked = overlayEnabled,
                    onCheckedChange = { isChecked ->
                        scope.launch {
                            prefsManager.setOverlayEnabled(isChecked)
                            if (isChecked) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
                                    val intent = Intent(
                                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                        Uri.parse("package:${context.packageName}")
                                    )
                                    context.startActivity(intent)
                                } else {
                                    context.startService(Intent(context, FloatingOverlayService::class.java))
                                }
                            } else {
                                context.stopService(Intent(context, FloatingOverlayService::class.java))
                            }
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = InkDark,
                        checkedTrackColor = CreamAccent
                    )
                )
            }
        }
    }
}
