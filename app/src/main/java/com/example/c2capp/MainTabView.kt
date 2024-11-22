package com.example.c2capp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainTabView(appState: AppState) {
    val navController = rememberNavController()
    var currentRoute by remember { mutableStateOf("home") }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    currentRoute = navBackStackEntry?.destination?.route ?: "home"

    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = currentRoute == "home",
                    onClick = {
                        if (currentRoute != "home") navController.navigate("home")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Share, contentDescription = "Bible") },
                    label = { Text("Bible") },
                    selected = currentRoute == "bible",
                    onClick = {
                        if (currentRoute != "bible") navController.navigate("bible")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Email, contentDescription = "Plans") },
                    label = { Text("Plans") },
                    selected = currentRoute == "plans",
                    onClick = {
                        if (currentRoute != "plans") navController.navigate("plans")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Discover") },
                    label = { Text("Discover") },
                    selected = currentRoute == "discover",
                    onClick = {
                        if (currentRoute != "discover") navController.navigate("discover")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "More") },
                    label = { Text("More") },
                    selected = currentRoute == "more",
                    onClick = {
                        if (currentRoute != "more") navController.navigate("more")
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = "home", Modifier.padding(innerPadding)) {
            composable("home") { HomeView() }
            composable("bible") { BibleView() }
            composable("plans") { PlansView() }
            composable("discover") { DiscoverView() }
            composable("more") { MoreView() }
        }
    }
}

