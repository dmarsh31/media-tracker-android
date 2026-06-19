package edu.metrostate.ics342.mediatracker.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.model.TokenRequest
import edu.metrostate.ics342.mediatracker.data.network.ApiConstants
import edu.metrostate.ics342.mediatracker.data.network.RetrofitInstance
import edu.metrostate.ics342.mediatracker.data.network.UserApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel() : ViewModel() {
    private val authApi = RetrofitInstance.userApiService

    sealed class AuthUiState {
        object Idle    : AuthUiState()
        object Loading : AuthUiState()
        object Success : AuthUiState()
        data class Error(val msgResId: Int) : AuthUiState()
    }

    // ── Login ─────────────────────────────────────────────────────────────
    private val _email    = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _loginState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val loginState: StateFlow<AuthUiState> = _loginState.asStateFlow()

    fun onEmailChange(value: String)    { _email.value    = value }
    fun onPasswordChange(value: String) { _password.value = value }

    fun onLoginClick() {
        viewModelScope.launch {
            _loginState.value = AuthUiState.Loading
            delay(800)
            try {
                val response = authApi.login(
                    TokenRequest(
                        grantType = password.value,
                        email = email.value,
                        password = password.value,
                        clientId = ApiConstants.CLIENT_ID,
                        clientSecret = ApiConstants.CLIENT_SECRET
                    )
                )
                Log.d("API_RESPONSE", "${response}")

                // response.accessToken should be here
                _loginState.value = AuthUiState.Success

            } catch (e: Exception) {
                Log.d("API_RESPONSE", "${e}")
                _loginState.value = AuthUiState.Error(
                    -1
                )
            }


            if (_email.value.isNotBlank() && _password.value.isNotBlank()) {

                _loginState.value = AuthUiState.Success
            } else {
                _loginState.value = AuthUiState.Error(edu.metrostate.ics342.mediatracker.R.string.error_empty_credentials)
            }
        }
    }

    fun resetLoginState() { _loginState.value = AuthUiState.Idle }

}
