package com.example.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.api.StaffApiService
import com.example.data.repository.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DashboardState(
    val pendingKyc: Int = 0,
    val pendingLoans: Int = 0,
    val pendingServices: Int = 0,
    val openSupport: Int = 0,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

class DashboardViewModel(
    private val apiService: StaffApiService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState: StateFlow<DashboardState> = _uiState.asStateFlow()

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true, error = null)
            try {
                // Try fetching real API, fallback to dummy data if network fails
                val kyc = apiService.getPendingKycCount().count
                val loans = apiService.getPendingLoansCount().count
                val services = apiService.getPendingServicesCount().count
                val support = apiService.getOpenSupportCount().count

                _uiState.value = _uiState.value.copy(
                    pendingKyc = kyc,
                    pendingLoans = loans,
                    pendingServices = services,
                    openSupport = support,
                    isRefreshing = false
                )
            } catch (e: Exception) {
                // Mock data for previewing without backend
                _uiState.value = _uiState.value.copy(
                    pendingKyc = 14,
                    pendingLoans = 8,
                    pendingServices = 3,
                    openSupport = 5,
                    isRefreshing = false,
                    error = "Using offline mock data"
                )
            }
        }
    }

    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            sessionManager.clearSession()
            onLogoutComplete()
        }
    }
}

class DashboardViewModelFactory(
    private val apiService: StaffApiService,
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(apiService, sessionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
