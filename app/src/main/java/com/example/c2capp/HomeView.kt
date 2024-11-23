package com.example.c2capp

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material.Text
import androidx.compose.ui.Modifier


@Composable
fun HomeView() {
    var tabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Today", "Community")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Home") })
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(selectedTabIndex = tabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        text = { Text(title) }
                    )
                }
            }
            when (tabIndex) {
                0 -> HomeFirstPageView()
                1 -> HomeSecondPageView()
            }
        }
    }
}



@Composable
fun HomeSecondPageView() {
    // Implement the UI for the "Community" tab
    Text("Content for Community")
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeView() {
    HomeView()
}
