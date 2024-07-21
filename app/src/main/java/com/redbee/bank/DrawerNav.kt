package com.redbee.bank

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redbee.bank.ui.theme.RedbeebankTheme

@Composable
fun DrawerNav(
    drawerState: DrawerState,
    activeRoute: Route? = Route.Home,
    onNavigate: (Route) -> Unit = {},
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Spacer(Modifier.height(20.dp))

                    Route.entries.forEach { route ->
                        NavigationDrawerItem(
                            icon = { route.Icon() },
                            label = { Text(route.label) },
                            selected = route == activeRoute,
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                            onClick = { onNavigate(route) },
                        )
                    }
                }
            }
        },
        content = content,
    )
}

@Preview(showBackground = true)
@Composable
internal fun DrawerNavPreview() {
    RedbeebankTheme {
        DrawerNav(rememberDrawerState(DrawerValue.Open)) { }
    }
}
