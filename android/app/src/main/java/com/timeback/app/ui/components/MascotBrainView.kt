package com.timeback.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun MascotBrainView(
    count: Int,
    modifier: Modifier = Modifier.size(130.dp)
) {
    val (baseColor, eyeColor, stage) = when {
        count < 50 -> Triple(Color(0xFFFFAAA6), Color(0xFF2B1600), 1)
        count < 100 -> Triple(Color(0xFFFFD07B), Color(0xFF2B1600), 2)
        count < 150 -> Triple(Color(0xFFFF9E64), Color(0xFF2B1600), 3)
        count < 200 -> Triple(Color(0xFFB3A28F), Color(0xFF2B1600), 4)
        else -> Triple(Color(0xFF665B54), Color(0xFFFC4A52), 5)
    }

    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension * 0.42f

        drawCircle(
            color = baseColor,
            radius = radius,
            center = center
        )

        val eyeRadius = 5.dp.toPx()
        val leftEye = Offset(center.x - radius * 0.35f, center.y - radius * 0.12f)
        val rightEye = Offset(center.x + radius * 0.35f, center.y - radius * 0.12f)

        if (stage == 5) {
            val xSize = 6.dp.toPx()
            drawLine(eyeColor, Offset(leftEye.x - xSize, leftEye.y - xSize), Offset(leftEye.x + xSize, leftEye.y + xSize), strokeWidth = 3.dp.toPx())
            drawLine(eyeColor, Offset(leftEye.x + xSize, leftEye.y - xSize), Offset(leftEye.x - xSize, leftEye.y + xSize), strokeWidth = 3.dp.toPx())
            drawLine(eyeColor, Offset(rightEye.x - xSize, rightEye.y - xSize), Offset(rightEye.x + xSize, rightEye.y + xSize), strokeWidth = 3.dp.toPx())
            drawLine(eyeColor, Offset(rightEye.x + xSize, rightEye.y - xSize), Offset(rightEye.x - xSize, rightEye.y + xSize), strokeWidth = 3.dp.toPx())

            drawArc(
                color = Color.Black,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(center.x - radius * 0.3f, center.y + radius * 0.2f),
                size = Size(radius * 0.6f, radius * 0.3f),
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )
        } else {
            drawCircle(eyeColor, eyeRadius, leftEye)
            drawCircle(eyeColor, eyeRadius, rightEye)

            val sweep = if (stage <= 2) 180f else -180f
            val start = 0f
            drawArc(
                color = eyeColor,
                startAngle = start,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = Offset(center.x - radius * 0.25f, center.y + radius * 0.15f),
                size = Size(radius * 0.5f, radius * 0.25f),
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}
