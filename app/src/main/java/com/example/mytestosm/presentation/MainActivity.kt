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
        PizzaModel(1, R.drawable.image_1, "Peperoni", "Острая пицца «Пепперони с халапеньо» — мексиканская забава из колбасных кружочков, устилающих основу, пропитывая ее чесночно-копченным ароматом. Вызывает сильный аппетит и настрой на плотный перекус. Халапеньо добавляет огонька и согревает изнутри, даже если пицца немного остыла."),
        PizzaModel(2, R.drawable.image_2, "Vegan", "Экзотика в нашем меню – пицца Vegan. Пробуя на вкус эту уникальную в своем роде пиццу, вы можете ощутить прилив энергии и сил, вызывающих желание страстно и горячо танцевать, под сопровождение жаркого гавайского танго. Именно в этом танце сплелась копченая курочка с ананасом и сыром молодых буйволиц — Моцарелла. "),
        PizzaModel(3, R.drawable.image_3, "Margaritta", "Утонченная, томатная и ароматная «Маргарита» слишком сладкая и сочная, чтобы забыть ее вкус. Не смотря на отсутствие мяса, курицы или колбасок, она-таки хороша! Все дело в хорошо отобранных помидорах, которые успели созреть, но не переспеть – в меру плотные, мягкие и приправленные соусом из собственной мякоти. "),
        PizzaModel(4, R.drawable.image_4, "Mexican", "Mexican одна из лидирующих позиций при заказе пиццы на большие компании. Решает проблемы на корню, как только в офисе или компании друзей завязывается спор — какую начинку выбрать. Разнообразие четырех вкусов способно удовлетворить гастрономические потребности каждого за столом."),
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


        /*  composable("itemProduct/{pizzaModel.id}") { backStackEntry ->
               val idM = backStackEntry.arguments?.getInt("pizzaModel.id")
               if (idM != null) {
                   val pizzaModel = infoPiz.get(id)// Получение модели продукта по id
                   ItemProductScreen(pizzaModel = pizzaModel)
               }
           }*/
        /*   composable("itemProduct/{id}") { backStackEntry ->
               val id = backStackEntry.arguments?.getInt("id")
               if (id != null) {
                   val pizzaModel = infoPiz.getOrNull(id)
                   if (pizzaModel != null) {
                       ItemProductScreen(pizzaModel = pizzaModel)
                   } else {
                       // Обработка ситуации, если модель продукта не найдена по указанному id
                   }
               } else {
                   // Обработка ситуации, если id не передано
               }
           }*/
        composable("itemProduct/{index}") { backStackEntry ->
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
