package com.example.mytestosm.presentation.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mytestosm.R
import com.example.mytestosm.presentation.utils.PizzaModel
import com.example.mytestosm.ui.theme.BackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemProductScreen(
    modifier: Modifier = Modifier,
    pizzaModel: PizzaModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = BackgroundColor),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_back),
                            contentDescription = "Back", Modifier.size(30.dp)
                        )
                    }
                }

            )
        },
    ) { innerPadding ->
        Box( modifier = Modifier
            .padding(innerPadding)
            .background(color = BackgroundColor)) {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(25.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Box(
                    modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = pizzaModel.image),
                        contentDescription = "",
                        modifier.clip(RoundedCornerShape(16.dp))
                    )
                }
                Text(text = pizzaModel.name, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Text(text = pizzaModel.description, fontSize = 18.sp)
            }
        }
    }
}
