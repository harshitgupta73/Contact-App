package com.example.phoneapp.ui_layer.resources

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun ProfileImageInitials(name: String) {
    // Combine first letter
    val initials = (name.take(1)).uppercase()

    // Generate a random color for the background
    val minColorValue = 0.3f
    val backgroundColor = Color(
        red = Random.nextFloat() * (1 - minColorValue) + minColorValue,
        green = Random.nextFloat() * (1 - minColorValue) + minColorValue,
        blue = Random.nextFloat() * (1 - minColorValue) + minColorValue
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(40.dp) // Adjust size as needed
    ) {
        // Draw a circle as background with random color
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(color = backgroundColor)
        }

        // Display initials on top of the circle
        Text(
            text = initials,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal
        )
    }
}