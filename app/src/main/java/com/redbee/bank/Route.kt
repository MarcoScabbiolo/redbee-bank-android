package com.redbee.bank

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.compose.material3.Icon as MaterialIcon

enum class Route {
    Home,
    Transfer,
    Cards,
    Loans,
    Investment,
    Profile,
    Settings,
}

val Route.label get(): String =
    when (this) {
        Route.Home -> "Inicio"
        Route.Transfer -> "Transferir"
        else -> name
    }

val Route.icon get(): ImageVector =
    when (this) {
        Route.Home -> Icons.Default.Home
        Route.Transfer -> Icons.Default.Send
        else -> Icons.Default.AutoGraph
    }

@Composable
fun Route.Icon() {
    MaterialIcon(this.icon, contentDescription = this.label)
}

fun NavGraphBuilder.route(
    route: Route,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(route.name, content = content)
}

val mockRoutes = Route.entries - Route.Home - Route.Transfer
