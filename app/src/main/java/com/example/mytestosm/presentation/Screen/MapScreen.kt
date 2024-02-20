package com.example.mytestosm.presentation.Screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavHostController
import com.example.mytestosm.presentation.compose.MapView
import com.example.mytestosm.presentation.compose.POINT_A
import com.example.mytestosm.presentation.compose.POINT_B
import com.example.mytestosm.presentation.compose.SearchEnd
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline



@Composable
fun MapScreen(navHostController: NavHostController) {
    Column(Modifier.fillMaxSize()) {

        MapView(
            modifier = Modifier.fillMaxSize(),
            onMapReady = { map ->
                // mapView = map
                addMarker1(map)
            },
            onMapClick = { geoPoint, mapView ->
                // Этот код выполнится при нажатии на карту
                addMarker(geoPoint, mapView)
            },
            onLongPress = ::handleLongPress,
            drawPolyline = { mapView, pointA, pointB ->
                drawPolyline(mapView, pointA, pointB)
            },
            navHostController
        )
    }
}
fun handleLongPress(geoPoint: GeoPoint, mapView: MapView?) {  // основная функция нажатия
    // Добавление метки при долгом нажатии на карту

    mapView?.let { map ->
        if (SearchEnd == false) {
            map.overlays.filterIsInstance<Marker>().forEach {
                map.overlays.remove(it)
                Log.i("SearchEnd = false", "removeALL")
            }
            // Добавление нового маркера
            val marker = Marker(map)
            marker.position = geoPoint
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            map.overlays.add(marker)
            map.invalidate()
            POINT_A = geoPoint
        } else {
            POINT_B = geoPoint
            map.overlays.filterIsInstance<Marker>().forEach { marker ->
                if (marker.position != POINT_A) {
                    map.overlays.remove(marker)
                }
                Log.i("SearchEnd = TRUE", "remove marker != POINT_A")
            }
            val marker = Marker(map)
            marker.position = geoPoint
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            map.overlays.add(marker)
            map.invalidate()


        }
    }
}

fun drawPolyline(mapView: MapView?, pointA: GeoPoint, pointB: GeoPoint) {
    mapView?.let { map ->
        val polyline = Polyline().apply {
            color = Color.Red.toArgb()
            width = 5f
            addPoint(pointA)
            addPoint(pointB)
        }
        map.overlays.add(polyline)
        map.invalidate()
    }
}


private fun addMarker1(mapView: MapView?) {
    mapView?.let { map ->
        val marker = Marker(map)
        marker.position = GeoPoint(52.520008, 13.404954) // Berlin coordinates
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(marker)
        map.invalidate()
    }
}

private fun addMarker(geoPoint: GeoPoint, mapView: MapView?) {
    mapView?.let { map ->
        val marker = Marker(map)
        marker.position = geoPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(marker)
        map.invalidate()
    }
}