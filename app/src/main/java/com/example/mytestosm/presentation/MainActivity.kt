package com.example.mytestosm.presentation


import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mytestosm.R
import com.example.mytestosm.presentation.Screen.ItemProductScreen
import com.example.mytestosm.presentation.Screen.ListProductsScreen
import com.example.mytestosm.presentation.Screen.MapScreen
import com.example.mytestosm.presentation.Screen.MenuScreen
import com.example.mytestosm.presentation.Screen.Unavailable
import com.example.mytestosm.presentation.utils.PizzaModel
import com.example.mytestosm.presentation.utils.Route
import com.example.mytestosm.ui.theme.MyTestOSMTheme
import org.osmdroid.config.Configuration


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(
            applicationContext,
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )
        setContent {
            MyTestOSMTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    val navHostController = rememberNavController()

                    MainGraph(navHostController = navHostController)

                }
            }
        }
    }
}

@Composable
fun MainGraph(
    navHostController: NavHostController
) {

    val infoPiz = arrayListOf<PizzaModel>(
        PizzaModel(1, R.drawable.image_1, "Peperoni", stringResource(id = R.string.info_1)),
        PizzaModel(2, R.drawable.image_2, "Vegan", stringResource(id = R.string.info_2)),
        PizzaModel(3, R.drawable.image_3, "Margaritta", stringResource(id = R.string.info_3)),
        PizzaModel(4, R.drawable.image_4, "Mexican", stringResource(id = R.string.info_4)),
    )
    NavHost(navController = navHostController, startDestination = Route.MAP_SCREEN) {
        composable(route = Route.MAP_SCREEN) {
            MapScreen(navHostController)
        }
        composable(Route.MENU_SCREEN) {
            MenuScreen(navHostController)
        }
        composable(Route.LIST_PRODUCTS_SCREEN) {
            ListProductsScreen(navHostController, infoPiz)
        }

        composable(Route.UNAVAILABLE) {
            Unavailable(navHostController)
        }

        composable(Route.ITEM_PRODUCT_SCREEN) { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull()
            if (index != null) {
                val pizzaModel = infoPiz.getOrNull(index)
                if (pizzaModel != null) {
                    ItemProductScreen(pizzaModel = pizzaModel, navController = navHostController)
                } else {
                    // Обработка ситуации, если модель продукта не найдена по указанному индексу
                }
            } else {
                // Обработка ситуации, если индекс не передан
            }
        }
    }
}
