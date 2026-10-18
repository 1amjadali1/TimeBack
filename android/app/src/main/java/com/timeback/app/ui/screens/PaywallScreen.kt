package com.timeback.app.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timeback.app.ui.theme.BgMidnight
import com.timeback.app.ui.theme.CreamAccent
import com.timeback.app.ui.theme.FreshGreen
import com.timeback.app.ui.theme.InkDark
import com.timeback.app.ui.theme.LineBorder
import com.timeback.app.ui.theme.SurfaceDark
import com.timeback.app.ui.theme.SurfaceElevated
import com.timeback.app.ui.theme.TextMuted
import com.timeback.app.ui.theme.TextPrimary

@Composable
fun PaywallScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgMidnight)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(CreamAccent)
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Text(
                text = "TIMEBACK PLUS",
                color = InkDark,
                fontWeight = FontWeight.Black,
                fontSize = 11.sp,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Reclaim Your Attention",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextPrimary
        )
        Text(
            text = "Hard reel blocking, strict limits, and zero distractions.",
            fontSize = 13.sp,
            color = TextMuted,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, CreamAccent, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceElevated)
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
                    Column {
                        Text(text = "Annual Pass", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text(text = "Save 50% vs monthly", fontSize = 11.sp, color = FreshGreen, fontWeight = FontWeight.Bold)
                    }
                    Text(text = "₹999/yr", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = CreamAccent)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        Toast.makeText(context, "Opening Google Play Store Billing (Yearly)...", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CreamAccent),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text(text = "Start 7-Day Free Trial", color = InkDark, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, LineBorder, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Monthly Pass", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(text = "Cancel anytime", fontSize = 11.sp, color = TextMuted)
                }
                Text(text = "₹99/mo", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            }
        }

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
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FeatureRow("🔒 Automatic Reel & Shorts Blocker", true)
                FeatureRow("⏱️ Custom Daily App Quotas", true)
                FeatureRow("🛑 Forced Breathing Pauses", true)
                FeatureRow("🚫 100% Ad-Free Experience", true)
            }
        }
    }
}

@Composable
fun FeatureRow(title: String, enabled: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 13.sp, color = TextPrimary)
        Text(text = if (enabled) "✓" else "—", color = if (enabled) FreshGreen else TextMuted, fontWeight = FontWeight.Bold)
    }
}
