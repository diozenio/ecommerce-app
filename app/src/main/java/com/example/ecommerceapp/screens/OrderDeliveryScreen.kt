package com.example.ecommerceapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.core.APIService
import com.example.ecommerceapp.data.maps.GoogleMapsClient
import com.google.maps.android.PolyUtil
import com.example.ecommerceapp.model.DeliveryPerson
import com.example.ecommerceapp.model.LocationCoordinate
import com.example.ecommerceapp.model.OrderStatus
import com.example.ecommerceapp.model.OrderDelivery
import com.example.ecommerceapp.model.OrderDeliveryStatus
import com.example.ecommerceapp.ui.components.UIButton
import com.example.ecommerceapp.ui.components.UIButtonVariant
import com.example.ecommerceapp.ui.components.UIIcon
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.components.UINavHeader
import com.example.ecommerceapp.ui.components.UIText
import com.example.ecommerceapp.ui.components.UITextVariant
import com.example.ecommerceapp.ui.components.UITextWeight
import com.example.ecommerceapp.ui.theme.Colors
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

private const val DEFAULT_ZOOM_LEVEL = 13f
private const val MIN_ZOOM_LEVEL = 10f
private const val MAX_ZOOM_LEVEL = 18f
private const val POLYLINE_WIDTH = 10f
private const val ROUTE_LANGUAGE = "pt-BR"
private const val ROUTE_MODE = "driving"

sealed interface OrderDeliveryUiState {
    data object Loading : OrderDeliveryUiState
    data class Success(val data: OrderDelivery) : OrderDeliveryUiState
    data class Error(val message: String) : OrderDeliveryUiState
}

private data class CameraConfig(
    val center: LatLng,
    val zoom: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDeliveryScreen(
    navController: NavHostController,
    orderId: String
) {
    var uiState by remember { mutableStateOf<OrderDeliveryUiState>(OrderDeliveryUiState.Loading) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    BackHandler(enabled = sheetState.isVisible) {
        scope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            showBottomSheet = false
        }
    }

    LaunchedEffect(orderId) {
        uiState = OrderDeliveryUiState.Loading
        
        val parsedId = orderId.toIntOrNull()
        uiState = if (parsedId == null) {
            OrderDeliveryUiState.Error("ID do pedido inválido")
        } else {
            try {
                val tracking = APIService.orderApi.getTracking(parsedId)
                OrderDeliveryUiState.Success(tracking)
            } catch (e: Exception) {
                OrderDeliveryUiState.Error("Não foi possível carregar os dados de rastreamento")
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OrderDeliveryHeader(
            onBackPressed = { navController.popBackStack() },
            onNotificationPressed = { /* TODO: Implementar notificações */ }
        )

        Box(modifier = Modifier.weight(1f)) {
            when (val state = uiState) {
                is OrderDeliveryUiState.Loading -> LoadingContent()
                is OrderDeliveryUiState.Error -> ErrorContent(message = state.message)
                is OrderDeliveryUiState.Success -> TrackingMapContent(
                    orderDeliveryData = state.data,
                    onShowDetails = { showBottomSheet = true }
                )
            }

            if (showBottomSheet && uiState is OrderDeliveryUiState.Success) {
                OrderStatusBottomSheet(
                    orderDeliveryData = (uiState as OrderDeliveryUiState.Success).data,
                    sheetState = sheetState,
                    onDismiss = { showBottomSheet = false }
                )
            }
        }
    }
}

@Composable
private fun OrderDeliveryHeader(
    onBackPressed: () -> Unit,
    onNotificationPressed: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.Primary0)
    ) {
        UINavHeader(
            title = "Track Order",
            onBackPressed = onBackPressed,
            onNotificationPressed = onNotificationPressed
        )
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Colors.Primary900)
    }
}

@Composable
private fun ErrorContent(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UIIcon(
            icon = UIIconName.WarningCircle,
            size = 64.dp,
            color = Colors.Primary300
        )
        Spacer(modifier = Modifier.height(20.dp))
        UIText(
            text = message,
            variant = UITextVariant.H4,
            weight = UITextWeight.SemiBold,
            color = Colors.Primary900
        )
    }
}

@Composable
private fun TrackingMapContent(
    orderDeliveryData: OrderDelivery,
    onShowDetails: () -> Unit
) {
    val currentLocation = orderDeliveryData.currentLocation.toLatLng()
    val destination = orderDeliveryData.destination.toLatLng()

    var routePoints by remember(currentLocation, destination) {
        mutableStateOf<List<LatLng>>(emptyList())
    }

    LaunchedEffect(currentLocation, destination) {
        routePoints = loadRoute(currentLocation, destination)
    }

    val cameraConfig = remember(routePoints, currentLocation) {
        calculateCameraConfig(routePoints, currentLocation)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cameraConfig.center, cameraConfig.zoom)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = createMapUiSettings(),
            properties = createMapProperties()
        ) {
            Marker(
                state = MarkerState(position = currentLocation),
                title = "Localização Atual",
                snippet = orderDeliveryData.currentLocation.address
            )

            Marker(
                state = MarkerState(position = destination),
                title = "Destino",
                snippet = orderDeliveryData.destination.address
            )

            if (routePoints.isNotEmpty()) {
                Polyline(
                    points = routePoints,
                    color = Colors.Primary900,
                    width = POLYLINE_WIDTH,
                    jointType = JointType.ROUND,
                    startCap = RoundCap(),
                    endCap = RoundCap()
                )
            }
        }

        OrderDetailsButton(
            onClick = onShowDetails,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

private suspend fun loadRoute(origin: LatLng, destination: LatLng): List<LatLng> {
    return try {
        val response = GoogleMapsClient.directionsApi.getDirections(
            origin = origin.toApiString(),
            destination = destination.toApiString(),
            apiKey = GoogleMapsClient.apiKey,
            mode = ROUTE_MODE,
            language = ROUTE_LANGUAGE
        )

        when {
            response.status == "OK" && !response.routes.isNullOrEmpty() -> {
                response.routes.first().overviewPolyline?.points
                    ?.takeIf { it.isNotEmpty() }
                    ?.let { PolyUtil.decode(it) }
                    ?: listOf(origin, destination)
            }
            else -> listOf(origin, destination)
        }
    } catch (e: Exception) {
        listOf(origin, destination)
    }
}

private fun LatLng.toApiString() = "$latitude,$longitude"

private fun calculateCameraConfig(
    routePoints: List<LatLng>,
    defaultLocation: LatLng
): CameraConfig {
    if (routePoints.isEmpty()) {
        return CameraConfig(defaultLocation, DEFAULT_ZOOM_LEVEL)
    }

    val bounds = LatLngBounds.builder().apply {
        routePoints.forEach { include(it) }
    }.build()

    return CameraConfig(
        center = bounds.center,
        zoom = calculateZoomLevel(bounds)
    )
}

private fun calculateZoomLevel(bounds: LatLngBounds): Float {
    val northeast = bounds.northeast
    val southwest = bounds.southwest
    val latDiff = northeast.latitude - southwest.latitude
    val lngDiff = northeast.longitude - southwest.longitude

    return when {
        latDiff > 0.1 || lngDiff > 0.1 -> 11f
        latDiff > 0.05 || lngDiff > 0.05 -> 12f
        latDiff > 0.02 || lngDiff > 0.02 -> 13f
        else -> 14f
    }
}

private fun createMapUiSettings() = MapUiSettings(
    zoomControlsEnabled = true,
    zoomGesturesEnabled = true,
    scrollGesturesEnabled = true,
    rotationGesturesEnabled = true,
    tiltGesturesEnabled = false,
    compassEnabled = false,
    myLocationButtonEnabled = false,
    mapToolbarEnabled = false
)

private fun createMapProperties() = MapProperties(
    mapType = MapType.NORMAL,
    isMyLocationEnabled = false,
    minZoomPreference = MIN_ZOOM_LEVEL,
    maxZoomPreference = MAX_ZOOM_LEVEL
)

@Composable
private fun OrderDetailsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Colors.Primary0),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UIIcon(
                icon = UIIconName.Details,
                size = 24.dp,
                color = Colors.Primary900
            )
            Spacer(modifier = Modifier.width(12.dp))
            UIText(
                text = "Ver Detalhes do Pedido",
                variant = UITextVariant.B1,
                weight = UITextWeight.SemiBold,
                color = Colors.Primary900,
                modifier = Modifier.weight(1f)
            )
            UIIcon(
                icon = UIIconName.Chevron,
                size = 24.dp,
                color = Colors.Primary500,
                modifier = Modifier.rotate(180f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderStatusBottomSheet(
    orderDeliveryData: OrderDelivery,
    sheetState: androidx.compose.material3.SheetState,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Colors.Primary0,
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            OrderStatusHeader(onDismiss = onDismiss)
            Spacer(modifier = Modifier.height(24.dp))
            
            orderDeliveryData.statusHistory.forEachIndexed { index, status ->
                OrderDeliveryStatusItem(
                    status = status.status,
                    location = status.location,
                    isCompleted = status.isCompleted,
                    isLast = index == orderDeliveryData.statusHistory.lastIndex
                )
            }

            orderDeliveryData.deliveryPerson?.let { deliveryPerson ->
                Spacer(modifier = Modifier.height(24.dp))
                DeliveryPersonCard(deliveryPerson = deliveryPerson)
            }
        }
    }
}

@Composable
private fun OrderStatusHeader(onDismiss: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UIText(
            text = "Order Status",
            variant = UITextVariant.H4,
            weight = UITextWeight.SemiBold,
            color = Colors.Primary900,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onDismiss) {
            Icon(
                painter = painterResource(R.drawable.cancel),
                contentDescription = "Fechar",
                tint = Colors.Primary900
            )
        }
    }
}

@Composable
private fun DeliveryPersonCard(deliveryPerson: DeliveryPerson) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Colors.Primary500),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = deliveryPerson.photo,
                contentDescription = "Foto do entregador",
                placeholder = painterResource(R.drawable.fallback_square),
                error = painterResource(R.drawable.fallback_square),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                UIText(
                    text = deliveryPerson.name,
                    variant = UITextVariant.B1,
                    weight = UITextWeight.SemiBold,
                    color = Colors.Primary900
                )
                UIText(
                    text = "Delivery Guy",
                    variant = UITextVariant.B2,
                    weight = UITextWeight.Regular,
                    color = Colors.Primary500
                )
            }

            IconButton(
                onClick = { /* TODO: Implementar chamada telefônica */ },
                modifier = Modifier
                    .size(40.dp)
                    .background(Colors.Primary900, CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.phone),
                    contentDescription = "Ligar",
                    tint = Colors.Primary0,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun OrderDeliveryStatusItem(
    status: OrderStatus,
    location: String,
    isCompleted: Boolean,
    isLast: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        StatusIndicator(isCompleted = isCompleted, isLast = isLast)
        Spacer(modifier = Modifier.width(16.dp))
        StatusInfo(
            status = status,
            location = location,
            isCompleted = isCompleted,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatusIndicator(isCompleted: Boolean, isLast: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = if (isCompleted) Colors.Primary900 else Colors.Primary200,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(Colors.Primary0, CircleShape)
                )
            }
        }

        if (!isLast) {
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(40.dp)
                    .background(
                        color = if (isCompleted) Colors.Primary900 else Colors.Primary200
                    )
            )
        }
    }
}

@Composable
private fun StatusInfo(
    status: OrderStatus,
    location: String,
    isCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        UIText(
            text = status.displayName,
            variant = UITextVariant.B1,
            weight = UITextWeight.SemiBold,
            color = if (isCompleted) Colors.Primary900 else Colors.Primary500
        )
        UIText(
            text = location,
            variant = UITextVariant.B3,
            weight = UITextWeight.Regular,
            color = Colors.Primary500
        )
    }
}

private fun LocationCoordinate.toLatLng() = LatLng(latitude, longitude)

private val OrderStatus.displayName: String
    get() = when (this) {
        OrderStatus.PACKING -> "Packing"
        OrderStatus.PICKED -> "Picked"
        OrderStatus.IN_TRANSIT -> "In Transit"
        OrderStatus.DELIVERED -> "Delivered"
    }

