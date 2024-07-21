package com.redbee.bank.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.redbee.bank.Icon
import com.redbee.bank.LocalBankNavigator
import com.redbee.bank.Route
import com.redbee.bank.label
import com.redbee.bank.ui.theme.RedbeebankTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val bankNavigator = LocalBankNavigator.current
    val scope = rememberCoroutineScope()

    HomeScreenPresentation(
        onTopBarClick = {
            scope.launch {
                bankNavigator.toggleNavDrawer()
            }
        },
        onNavigate = bankNavigator::navigate,
    )
}

internal val frequentActions = Route.entries - Route.Home

internal fun Modifier.contentPadding() = this.then(Modifier.padding(horizontal = 16.dp))

internal data class Transaction(
    val id: Int,
    val amount: Double,
    val description: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreenPresentation(
    onTopBarClick: () -> Unit,
    onNavigate: (Route) -> Unit,
) {
    val frequentActionsScroll = rememberScrollState()
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val transactions =
        remember {
            (0..100).map {
                Transaction(it, (0..1000).random().toDouble() * (-1..1).random(), "Description $it")
            }.reversed()
        }

    Scaffold(
        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                title = { Text("Tu Banco") },
                scrollBehavior = topAppBarScrollBehavior,
                navigationIcon = {
                    IconButton(
                        onClick = onTopBarClick,
                        colors =
                            IconButtonDefaults
                                .iconButtonColors()
                                .copy(contentColor = MaterialTheme.colorScheme.primary),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Abrir menú de navegación",
                        )
                    }
                },
            )
        },
    ) {
        Column(
            modifier =
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(it),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            ElevatedCard(
                elevation = CardDefaults.cardElevation(4.dp),
                modifier =
                    Modifier
                        .contentPadding()
                        .fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("Saldo disponible")
                    Text(
                        text = "$ 100.000.000",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            SectionTitle("Acciones frecuentes")
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.horizontalScroll(frequentActionsScroll).contentPadding(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                frequentActions.forEach {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 15.dp),
                    ) {
                        IconButton(
                            colors = IconButtonDefaults.filledIconButtonColors(),
                            onClick = { onNavigate(it) },
                        ) {
                            it.Icon()
                        }
                        Text(it.label)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            SectionTitle("Últimas transacciones")
            Spacer(modifier = Modifier.height(10.dp))
            transactions.forEach {
                Row(modifier = Modifier.contentPadding().padding(bottom = 5.dp)) {
                    Text(it.description)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("$ ${it.amount}", color = if (it.amount < 0) Color.Red else MaterialTheme.typography.bodySmall.color)
                }
            }
        }
    }
}

@Composable
internal fun SectionTitle(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.contentPadding(),
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    RedbeebankTheme {
        HomeScreenPresentation({}, {})
    }
}
