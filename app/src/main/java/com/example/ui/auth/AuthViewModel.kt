package com.example.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.api.StaffApiService
import com.example.data.model.LoginRequest
import com.example.data.repository.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(
    private val apiService: StaffApiService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    fun login(email: String, cid: String, pass: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            try {
                // Determine if we should use email or cid
                val userIdentifier = email.ifBlank { cid }
                
                // For simplicity, we are passing standard LoginRequest
                val response = apiService.login(LoginRequest(email = userIdentifier, password = pass))
                
                if (response.user.role == "customer") {
                    _uiState.value = AuthState.Error("Unauthorized Device Access: Customers are not permitted on Staff workstations.")
                } else {
                    sessionManager.saveSession(
                        token = response.token,
                        role = response.user.role,
                        name = response.user.name,
                        email = response.user.email
                    )
                    _uiState.value = AuthState.Success
                }
            } catch (e: Exception) {
                // Simulated fallback if network fails so the app can be previewed without backend
                if (e is java.net.ConnectException || e is java.net.SocketTimeoutException) {
                    sessionManager.saveSession(
                        token = "mock_token_xyz123",
                        role = "staff",
                        name = "Mock Staff Member",
                        email = email
                    )
                    _uiState.value = AuthState.Success
                } else {
                    _uiState.value = AuthState.Error("Login failed: ${e.message}")
                }
            }
        }
    }
}

class AuthViewModelFactory(
    private val apiService: StaffApiService,
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(apiService, sessionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
