package com.redbee.bank

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.facebook.react.ReactApplication
import com.redbee.bank.screens.HomeScreen
import com.redbee.bank.screens.MockScreen
import com.redbee.bank.ui.theme.RedbeebankTheme

class MainActivity :
    FragmentActivity(),
    ReactApplication by ReactApplicationActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateRnActivity(this.application)

        setContent {
            val scope = rememberCoroutineScope()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val navController = rememberNavController()
            val bankNavigator = BankNavigator(scope, navController, drawerState)

            RedbeebankTheme {
                CompositionLocalProvider(LocalBankNavigator provides bankNavigator) {
                    ReactModuleManager(activity = this) {
                        DrawerNav(
                            drawerState = drawerState,
                            activeRoute = currentRoute(),
                            onNavigate = bankNavigator::navigate,
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = Route.Home.name,
                            ) {
                                route(Route.Home) { HomeScreen() }
                                route(Route.Transfer) { ReactModule(RNModule.Transfer) }

                                mockRoutes.forEach { r ->
                                    route(r) { MockScreen(r) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
