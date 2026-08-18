package com.example.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.StaffApplication
import com.example.ui.auth.AuthScreen
import com.example.ui.auth.AuthViewModel
import com.example.ui.auth.AuthViewModelFactory
import com.example.ui.dashboard.DashboardScreen
import com.example.ui.dashboard.DashboardViewModel
import com.example.ui.dashboard.DashboardViewModelFactory
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

object Routes {
    const val AUTH = "auth"
    const val DASHBOARD = "dashboard"
    const val CUSTOMER_LIST = "customer_list"
    const val CUSTOMER_DETAIL = "customer_detail/{id}"
    const val KYC_REVIEW = "kyc_review"
    const val LOAN_APPROVAL = "loan_approval"
    const val SERVICE_REQUESTS = "service_requests"
    const val SUPPORT_DESK = "support_desk"
    const val AUDIT_LOG = "audit_log"
}

@Composable
fun StaffNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val container = (context.applicationContext as StaffApplication).container
    
    // Determine start destination synchronously for initial load
    val token = runBlocking { container.sessionManager.tokenFlow.firstOrNull() }
    val startDest = if (token.isNullOrEmpty()) Routes.AUTH else Routes.AUTH // Always require auth/biometric on start

    NavHost(navController = navController, startDestination = startDest) {
        composable(Routes.AUTH) {
            val factory = AuthViewModelFactory(container.apiService, container.sessionManager)
            val viewModel: AuthViewModel = viewModel(factory = factory)
            AuthScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.AUTH) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.DASHBOARD) {
            val factory = DashboardViewModelFactory(container.apiService, container.sessionManager)
            val viewModel: DashboardViewModel = viewModel(factory = factory)
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToCustomers = { navController.navigate(Routes.CUSTOMER_LIST) },
                onLogout = {
                    navController.navigate(Routes.AUTH) {
                        popUpTo(0)
                    }
                }
            )
        }
        composable(Routes.KYC_REVIEW) {
            com.example.ui.kyc.KycReviewScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.LOAN_APPROVAL) {
            StubScreen("Loan Approval Portal")
        }
        composable(Routes.SERVICE_REQUESTS) {
            StubScreen("Service Requests")
        }
        composable(Routes.SUPPORT_DESK) {
            StubScreen("Support Desk")
        }
        composable(Routes.CUSTOMER_LIST) {
            com.example.ui.customers.CustomerListScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun StubScreen(title: String) {
    Box(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.Text(title, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
    }
}
