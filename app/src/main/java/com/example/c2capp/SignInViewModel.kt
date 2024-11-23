import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

// Data class for the login request
data class SignInRequest(
    val email: String,
    val password: String)

// Retrofit API service interface for login
interface ApiService1 {
    @POST("account/login")
    suspend fun login(@Body request: SignInRequest): Result<Unit>
}

// AppState to manage user authentication state across the app


class SignInViewModel: ViewModel() {
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _showAlert = MutableStateFlow(false)
    private val _statusMessage = MutableStateFlow("")
    private val _authenticationSuccess = MutableStateFlow(false)

    val email: StateFlow<String> = _email
    val password: StateFlow<String> = _password
    val showAlert: StateFlow<Boolean> = _showAlert
    val statusMessage: StateFlow<String> = _statusMessage
    val authenticationSuccess: StateFlow<Boolean> = _authenticationSuccess

    // Retrofit setup
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://covertocoverbible.org/api/") // Change to your actual URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService1 = retrofit.create(ApiService1::class.java)

    fun signInUser() {
        viewModelScope.launch {
            val request = SignInRequest(_email.value, _password.value)
            try {
                val response = apiService.login(request)
                if (response.isSuccess) {
                    _statusMessage.value = "Authentication successful."
                    _authenticationSuccess.value = true
                } else {
                    _statusMessage.value = "Authentication failed."
                    _authenticationSuccess.value = false
                }
            } catch (e: HttpException) {
                _statusMessage.value = "Authentication failed: ${e.message}"
                _authenticationSuccess.value = false
            } catch (e: Exception) {
                _statusMessage.value = "Error occurred: ${e.localizedMessage}"
                _authenticationSuccess.value = false
            }
            _showAlert.value = true
        }
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun dismissAlert() {
        _showAlert.value = false
    }
}


