package com.redbee.bank

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

private fun NavDestination.toRoute(): Route? = route?.runCatching { enumValueOf<Route>(this) }?.getOrNull()

class BankNavigator(
    private val scope: CoroutineScope,
    private val navController: NavController,
    private var drawerState: DrawerState,
) {
    val route = navController.currentBackStackEntryFlow.mapNotNull { it.destination.toRoute() }

    val currentRoute: Route? = navController.currentDestination?.toRoute()

    fun navigate(route: Route) {
        navController.navigate(route.name)
        closeNavDrawer()
    }

    fun toggleNavDrawer() {
        if (drawerState.isOpen) closeNavDrawer() else openNavDrawer()
    }

    fun openNavDrawer() {
        scope.launch { drawerState.open() }
    }

    fun closeNavDrawer() {
        scope.launch { drawerState.close() }
    }
}

val LocalBankNavigator =
    staticCompositionLocalOf<BankNavigator> {
        error("CompositionLocal BankNavigator not present")
    }

@Composable
fun BankNavigator.route() = route.collectAsState(initial = currentRoute).value

@Composable
fun currentRoute() = LocalBankNavigator.current.route()
