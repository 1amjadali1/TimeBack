package com.timeback.app.ui.screens

import android.content.Context
import android.content.Intent
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timeback.app.data.PreferencesManager
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
fun BattleScreen(
    context: Context,
    prefsManager: PreferencesManager
) {
    val userCount by prefsManager.totalReelsFlow.collectAsState(initial = 0)
    val friendCount = 84

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgMidnight)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "TimeBack Battle Ring",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextPrimary
        )
        Text(
            text = "Compare reel counts daily. Lower count takes the trophy.",
            fontSize = 12.sp,
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp)),
            colors = CardDefaults.cardColors(containerColor = CreamAccent)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "TODAY'S 1v1 MATCHUP",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = InkDark,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "🥊", fontSize = 42.sp)
                        Text(text = "YOU", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = InkDark)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(InkDark)
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(text = "$userCount", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = CreamAccent)
                        }
                    }

                    Text(text = "VS", fontSize = 26.sp, fontWeight = FontWeight.Black, color = InkDark)

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "🥊", fontSize = 42.sp)
                        Text(text = "FRIEND", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = InkDark)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(InkDark)
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(text = "$friendCount", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = CreamAccent)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                val winning = userCount < friendCount
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(if (winning) FreshGreen else InkDark)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = if (winning) "🏆 You are winning today!" else "⚠️ Friend is ahead!",
                        color = if (winning) InkDark else CreamAccent,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, LineBorder, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceElevated)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Invite a Buddy",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Share your TimeBack code so you both keep each other accountable.",
                    fontSize = 12.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )

                Button(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "Let's battle on TimeBack to scroll less and get our time back! Join me with code TIMEBACK-100: https://play.google.com/store/apps/details?id=com.timeback.app")
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share TimeBack Battle"))
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CreamAccent),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text(text = "Share Code: TIMEBACK-100", color = InkDark, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
