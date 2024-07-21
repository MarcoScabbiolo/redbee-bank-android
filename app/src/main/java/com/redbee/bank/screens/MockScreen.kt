package com.redbee.bank.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.redbee.bank.Route
import com.redbee.bank.label
import com.redbee.bank.ui.theme.RedbeebankTheme

@Composable
fun MockScreen(route: Route) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(route.label + " Mock Screen", fontSize = 30.sp)
        }
    }
}

@Preview
@Composable
internal fun MockScreenPreview() {
    RedbeebankTheme {
        MockScreen(Route.Cards)
    }
}
