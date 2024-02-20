package com.example.mytestosm.presentation.Screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mytestosm.R
import com.example.mytestosm.presentation.compose.ItemColumnMenu
import com.example.mytestosm.presentation.compose.ItemsPizza
import com.example.mytestosm.presentation.utils.PizzaModel
import com.example.mytestosm.ui.theme.BackgroundColor
import com.example.mytestosm.ui.theme.Greq

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListProductsScreen(navController: NavHostController,infoPiz: ArrayList<PizzaModel>) {
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
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = BackgroundColor)
        ) {
            Column {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {

                    itemsIndexed(infoPiz) { index, pizzaModel ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clickable {
                                    // Навигация к ItemProductScreen с данными конкретного элемента
                                    navController.navigate("itemProduct/$index")
                                }
                        ) {
                            ItemsPizza(pizzaModel = pizzaModel)
                        }

                    }
                }
            }
        }
    }
}






