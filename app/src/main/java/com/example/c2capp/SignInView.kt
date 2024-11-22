import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.c2capp.AppState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInView(
    navController: NavController,
    appState: AppState,
    viewModel: SignInViewModel = viewModel() // Ensure the correct ViewModel instance is provided here
)
{
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()
    val showAlert by viewModel.showAlert.collectAsState()
    val authenticationSuccess by viewModel.authenticationSuccess.collectAsState()

    // Navigate to mainTab if authentication is successful
    LaunchedEffect(authenticationSuccess) {
        if (authenticationSuccess) {
            appState.logIn() // Update login status in AppState
            navController.navigate("mainTab") {
                popUpTo("signIn") { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Sign In") })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Email input field
                OutlinedTextField(
                    value = email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Password input field
                OutlinedTextField(
                    value = password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                )

                // Sign In button
                Button(
                    onClick = { viewModel.signInUser() },
                    enabled = email.isNotEmpty() && password.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Sign In")
                }

                // Display status message
                if (statusMessage.isNotEmpty()) {
                    Text(
                        text = statusMessage,
                        color = if (authenticationSuccess) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            // Alert dialog for authentication status
            if (showAlert) {
                AlertDialog(
                    onDismissRequest = { viewModel.dismissAlert() },
                    title = { Text("Authentication Status") },
                    text = { Text(statusMessage) },
                    confirmButton = {
                        TextButton(onClick = { viewModel.dismissAlert() }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    )
}
