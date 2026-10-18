package com.timeback.app.ui.screens

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timeback.app.ui.theme.CreamAccent
import com.timeback.app.ui.theme.InkDark
import com.timeback.app.ui.theme.SurfaceDark
import com.timeback.app.ui.theme.TextMuted
import com.timeback.app.ui.theme.TextPrimary

@Composable
fun PermissionDisclosureDialog(
    context: Context,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SurfaceDark,
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(
                text = "Accessibility Permission",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = CreamAccent
            )
        },
        text = {
            Column {
                Text(
                    text = "TimeBack uses the AccessibilityService API strictly to detect when you scroll short-form videos (Instagram Reels, YouTube Shorts, Facebook Reels, Snapchat Spotlight) to display your live reel count and enforce daily digital wellbeing limits.",
                    fontSize = 13.sp,
                    color = TextPrimary,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "🔒 Privacy Commitment: TimeBack never reads your personal messages, records keystrokes, tracks private accounts, or sends personal content to any server.",
                    fontSize = 12.sp,
                    color = TextMuted,
                    lineHeight = 16.sp
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = CreamAccent),
                shape = RoundedCornerShape(999.dp)
            ) {
                Text(
                    text = "Enable in Settings",
                    color = InkDark,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Not Now", color = TextMuted)
            }
        }
    )
}
