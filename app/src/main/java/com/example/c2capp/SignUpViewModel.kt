import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// Data class for the request body
data class RegisterRequest(
    val Email: String,
    val Password: String,
    val ConfirmPassword: String
)

// Retrofit API service interface
interface ApiService {
    @POST("account/register")
    suspend fun register(@Body request: RegisterRequest): String
}

class SignUpViewModel : ViewModel() {
    // Mutable state for each field and message
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _showAlert = MutableStateFlow(false)
    val showAlert: StateFlow<Boolean> = _showAlert

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> = _statusMessage

    private val _registrationSuccess = MutableStateFlow(false)
    val registrationSuccess: StateFlow<Boolean> = _registrationSuccess

    // Retrofit setup
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://covertocoverbible.org/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    // Function to register a new user
    fun registerUser() {
        viewModelScope.launch {
            val request = RegisterRequest(email.value, password.value, confirmPassword.value)
            try {
                val responseBody = apiService.register(request)
                _statusMessage.value = "Registration successful"
                _registrationSuccess.value = true
                println("Response Body: $responseBody")
            } catch (e: HttpException) {
                _statusMessage.value = "Error: ${e.message()}"
                _registrationSuccess.value = false
                println("Error occurred: ${e.message()}")
            } catch (e: Exception) {
                _statusMessage.value = "Error occurred: ${e.localizedMessage}"
                _registrationSuccess.value = false
                println("Error occurred: ${e.localizedMessage}")
            }
            _showAlert.value = true
        }
    }

    // Placeholder function for Google Sign-In
    fun signInWithGoogle() {
        // Implement Google sign-in logic here
        println("Signing in with Google")
    }

    // Placeholder function for Facebook Sign-In
    fun signInWithFacebook() {
        // Implement Facebook sign-in logic here
        println("Signing in with Facebook")
    }
    fun onNameChange(newName: String) { // Add this function
        _name.value = newName
    }
    // Helper functions to update state fields
    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun dismissAlert() {
        _showAlert.value = false
    }

}
