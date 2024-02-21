package com.example.mytestosm.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mytestosm.presentation.utils.PizzaModel

@Composable
fun ItemsPizza(pizzaModel: PizzaModel) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .background(shape = RoundedCornerShape(10.dp), color = Color.White)
    ) {
        Row(Modifier.padding(end = 16.dp, start = 16.dp, top = 5.dp, bottom = 5.dp)) {
            Image(
                painter = painterResource(id = pizzaModel.image),
                contentDescription = "imagePizza",
                Modifier.size(60.dp)
            )
            Text(
                text = pizzaModel.name,
                style = MaterialTheme.typography.displayLarge,
                color = Color.Black,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}