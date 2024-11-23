package com.example.c2capp

import SignInView
import SignUpView
import SignUpViewModel
import SignInViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.c2capp.ui.theme.C2cAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            C2cAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent()
                }
            }
        }
    }
}


@Composable

fun AppContent() {
    val navController = rememberNavController()
    val appState: AppState = viewModel() // App-wide instance of AppState

    // Observe login status and navigate accordingly
    LaunchedEffect(appState.isLoggedIn.value) {
        if (appState.isLoggedIn.value) {
            navController.navigate("mainTab") {
                popUpTo("auth") { inclusive = true }
            }
        } else {
            navController.navigate("signIn") {
                popUpTo("auth") { inclusive = true }
            }
        }
    }

    NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
            // Placeholder route to check login state
        }

        composable("signIn") { backStackEntry ->
            // Correctly scoped ViewModel instance
            val signInViewModel: SignInViewModel = viewModel(backStackEntry)
            // Pass the scoped ViewModel instance to SignInView
            SignInView(navController = navController, appState = appState, viewModel = signInViewModel)
        }

        composable("signUp") { backStackEntry ->
            // Correctly scoped ViewModel instance
            val signUpViewModel: SignUpViewModel = viewModel(backStackEntry)
            // Pass the scoped ViewModel instance to SignUpView
            SignUpView(navController = navController, appState = appState, viewModel = signUpViewModel)
        }

        composable("mainTab") {
            MainTabView(appState = appState)
        }
    }
}




