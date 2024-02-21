package com.example.mytestosm.presentation.compose

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.mytestosm.R
import com.example.mytestosm.presentation.utils.Route

import com.example.mytestosm.ui.theme.Click
import com.example.mytestosm.ui.theme.ClickOn
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay

var POINT_A: GeoPoint? = null
var POINT_B: GeoPoint? = null
var SearchEnd: Boolean = false

@SuppressLint("SuspiciousIndentation")
@Composable
fun MapView(
    modifier: Modifier = Modifier,
    onMapReady: (MapView) -> Unit,
    onMapClick: (GeoPoint, MapView) -> Unit,
    onLongPress: (GeoPoint, MapView) -> Unit,
    drawPolyline: (MapView, GeoPoint, GeoPoint) -> Unit,
    navHostController: NavHostController
) {

    val font = FontFamily(Font(R.font.font_medium))


    Column(Modifier.fillMaxSize()) {

        var pointA by remember { mutableStateOf<GeoPoint?>(null) }
        var pointB by remember { mutableStateOf<GeoPoint?>(null) }
        var clickableBtn by remember { mutableStateOf<Boolean>(true) }
        var SearchEndA by remember {
            mutableStateOf<Boolean>(value = false)
        }
        val context = LocalContext.current
        val cityCoordinates = GeoPoint(55.7558, 37.6173)

        val mapView = remember {
            MapView(context).apply {

                val tapOverlay = MapEventsOverlay(object : MapEventsReceiver {
                    override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                        // Do whatever
                        return true
                    }

                    override fun longPressHelper(p: GeoPoint?): Boolean {
                        p?.let { geoPoint ->
                            onLongPress(geoPoint, this@apply)
                        }
                        return true
                    }
                })
                this.overlays.add(tapOverlay)
                setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
                setMultiTouchControls(true)
                controller.setZoom(10.0)
                controller.setCenter(cityCoordinates)
                onMapReady(this)
                setOnClickListener { event ->
                    Log.i("setOnClickListenerMAP", "${event.x},${event.y}")
                    onMapClick(geoPointFromPoint(event.x, event.y, this), this)
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth()
        ) {

            AndroidView(
                factory = { mapView },
                modifier = modifier.fillMaxSize()
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .padding(5.dp), contentAlignment = Alignment.BottomCenter
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(end = 16.dp, start = 16.dp)
            ) {

                Button(
                    onClick = {
                        if (POINT_A == null) {
                            context.showToast("Не выбран маркер А", Toast.LENGTH_LONG)
                        } else {
                            pointA = POINT_A
                            SearchEnd = !SearchEnd
                            SearchEndA = SearchEnd
                        }

                    },
                    Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Click,
                        contentColor = Color.White,
                        disabledContainerColor = ClickOn,
                        disabledContentColor = Color.Gray
                    )
                ) {
                    SearchEndA = SearchEnd
                    if (!SearchEndA) {
                        Text(text = "Закрепить точку А", fontFamily = font)
                    } else {
                        Text(text = "Точка А закреплена", fontFamily = font)

                    }
                }
                Button(
                    onClick = {
                        if (POINT_B == null) {
                            context.showToast("Не выбран маркер B", Toast.LENGTH_LONG)
                        } else {
                            clickableBtn = false
                            POINT_A?.let { pointA ->
                                POINT_B?.let { pointB ->
                                    drawPolyline(mapView, pointA, pointB)
                                }
                            }
                        }
                    },
                    Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Click,
                        contentColor = Color.White,
                        disabledContainerColor = ClickOn,
                        disabledContentColor = Color.Gray
                    ), enabled = clickableBtn
                ) {
                    Text(text = "Связать", fontFamily = font)
                }
                Button(
                    onClick = { navHostController.navigate(Route.MENU_SCREEN) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Click,
                        contentColor = Color.White,
                        disabledContainerColor = ClickOn,
                        disabledContentColor = Color.Gray
                    )
                ) {
                    Text(text = "Меню", fontFamily = font)
                }
            }
        }
    }
}


fun Context.showToast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}


fun geoPointFromPoint(x: Float, y: Float, mapView: MapView): GeoPoint {

    val projection = mapView.projection
    val point = projection.fromPixels(x.toInt(), y.toInt())
    Log.i("geoPointFromPoint", "${point.latitude} , ${point.longitude}")
    return GeoPoint(point.latitude, point.longitude)
}