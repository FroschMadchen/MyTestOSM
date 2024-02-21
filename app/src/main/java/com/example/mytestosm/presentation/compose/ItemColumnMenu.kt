package com.example.mytestosm.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ItemColumnMenu(text: String, navController: NavHostController, destination: String) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { navController.navigate(destination) }
            .background(shape = RoundedCornerShape(10.dp), color = Color.White)
    ) {
        Row(Modifier.padding(end = 16.dp, start = 16.dp)) {
            Text(text = text, style = MaterialTheme.typography.displayLarge, color = Color.Black)
        }
    }
}