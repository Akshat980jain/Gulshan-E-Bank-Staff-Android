package com.example.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.util.BiometricHelper

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp > 600

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AuthState.Success -> onLoginSuccess()
            is AuthState.Error -> errorMsg = state.message
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
            .padding(if (isTablet) 32.dp else 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(if (isTablet) 0.9f else 1f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            if (isTablet) {
                Row(modifier = Modifier.fillMaxWidth().height(600.dp)) {
                    Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                        AuthDarkSection(isMobile = false)
                    }
                    Box(modifier = Modifier.weight(1.2f).fillMaxHeight().verticalScroll(rememberScrollState())) {
                        AuthLightSection(
                            isMobile = false,
                            email = email,
                            password = password,
                            onEmailChange = { email = it },
                            onPasswordChange = { password = it },
                            errorMsg = errorMsg,
                            uiState = uiState,
                            onLogin = {
                                errorMsg = null
                                viewModel.login(email, "", password)
                            },
                            onBiometric = {
                                errorMsg = null
                                if (context is FragmentActivity) {
                                    BiometricHelper.authenticate(
                                        activity = context,
                                        onSuccess = { viewModel.login("biometric_user@staff.com", "", "mock_pass") },
                                        onError = { errorMsg = "Biometric Error: $it" }
                                    )
                                } else {
                                    errorMsg = "Biometric not supported"
                                }
                            }
                        )
                    }
                }
            } else {
                Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
                    AuthDarkSection(isMobile = true)
                    AuthLightSection(
                        isMobile = true,
                        email = email,
                        password = password,
                        onEmailChange = { email = it },
                        onPasswordChange = { password = it },
                        errorMsg = errorMsg,
                        uiState = uiState,
                        onLogin = {
                            errorMsg = null
                            viewModel.login(email, "", password)
                        },
                        onBiometric = {
                            errorMsg = null
                            if (context is FragmentActivity) {
                                BiometricHelper.authenticate(
                                    activity = context,
                                    onSuccess = { viewModel.login("biometric_user@staff.com", "", "mock_pass") },
                                    onError = { errorMsg = "Biometric Error: $it" }
                                )
                            } else {
                                errorMsg = "Biometric not supported"
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AuthDarkSection(isMobile: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0F172A))
            .padding(if (isMobile) 24.dp else 32.dp),
        verticalArrangement = if (isMobile) Arrangement.Top else Arrangement.SpaceBetween
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(if (isMobile) 32.dp else 40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.AccountBalance, contentDescription = null, tint = Color.White, modifier = Modifier.size(if (isMobile) 16.dp else 20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Gulshan EBank", color = Color.White, style = if(isMobile) MaterialTheme.typography.titleSmall else MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("STAFF OPERATIONS PORTAL", color = Color.White.copy(alpha = 0.7f), fontSize = 9.sp, letterSpacing = 1.sp)
                }
            }

            Spacer(modifier = Modifier.height(if (isMobile) 24.dp else 48.dp))

            Surface(
                color = Color(0xFF10B981).copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.3f))
            ) {
                Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("256-Bit Encrypted Operations", color = Color(0xFF10B981), fontSize = 11.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 24.dp))

            Text("Secure Staff Workstation", color = Color.White, style = if (isMobile) MaterialTheme.typography.titleLarge else MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            
            if (!isMobile) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Verify customer identity, process loan applications, manage accounts, and respond to support requests.", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(48.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(32.dp))

                ChecklistItem("Customer KYC & Account Management")
                Spacer(modifier = Modifier.height(16.dp))
                ChecklistItem("Loan Application Approval Workflow")
                Spacer(modifier = Modifier.height(16.dp))
                ChecklistItem("Real-Time Transaction Audit Monitor")
            }
        }

        if (!isMobile) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Employee Desk: 1800-419-0000", color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
                Text("v1.0.0", color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ChecklistItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF10B981).copy(alpha = 0.8f), modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun AuthLightSection(
    isMobile: Boolean = false,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    errorMsg: String?,
    uiState: AuthState,
    onLogin: () -> Unit,
    onBiometric: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(if (isMobile) 24.dp else 40.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
            Column {
                Text("Log in to Staff Portal", style = if(isMobile) MaterialTheme.typography.titleLarge else MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                if (!isMobile) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Enter your employee credentials to access workstation", color = Color(0xFF64748B), fontSize = 14.sp)
                }
            }
            if (!isMobile) {
                Surface(
                    color = Color(0xFFEFF6FF),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDBEAFE))
                ) {
                    Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Staff Portal", color = Color(0xFF2563EB), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 32.dp))

        // Fake Segmented Control Tabs
        Surface(
            color = Color(0xFFF8FAFC),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF1F5F9))
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f),
                    shadowElevation = 1.dp
                ) {
                    Text("Staff Portal", textAlign = androidx.compose.ui.text.style.TextAlign.Center, modifier = Modifier.padding(vertical = if(isMobile) 8.dp else 10.dp), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A))
                }
                Text("KYC Desk", textAlign = androidx.compose.ui.text.style.TextAlign.Center, modifier = Modifier.weight(1f).padding(vertical = if(isMobile) 8.dp else 10.dp), color = Color(0xFF64748B), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                Text("Operations", textAlign = androidx.compose.ui.text.style.TextAlign.Center, modifier = Modifier.weight(1f).padding(vertical = if(isMobile) 8.dp else 10.dp), color = Color(0xFF64748B), fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(if(isMobile) 24.dp else 32.dp))

        Text("STAFF EMAIL ID", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B), letterSpacing = 0.5.sp)
        Spacer(modifier = Modifier.height(if(isMobile) 4.dp else 8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = { Text("staff@gulshan-ebank.com", color = Color(0xFF94A3B8), fontSize = 14.sp) },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF94A3B8)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE2E8F0),
                focusedBorderColor = Color(0xFF2563EB),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(if(isMobile) 16.dp else 24.dp))

        Text("STAFF PASSWORD", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B), letterSpacing = 0.5.sp)
        Spacer(modifier = Modifier.height(if(isMobile) 4.dp else 8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = { Text("Enter staff password", color = Color(0xFF94A3B8), fontSize = 14.sp) },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF94A3B8)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE2E8F0),
                focusedBorderColor = Color(0xFF2563EB),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            singleLine = true
        )

        if (errorMsg != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMsg ?: "", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(if(isMobile) 24.dp else 32.dp))

        Button(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth().height(if(isMobile) 48.dp else 56.dp),
            enabled = uiState != AuthState.Loading,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (uiState == AuthState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
            } else {
                Text("Secure Staff Login →", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(if(isMobile) 12.dp else 16.dp))
        
        OutlinedButton(
            onClick = onBiometric,
            modifier = Modifier.fillMaxWidth().height(if(isMobile) 48.dp else 56.dp),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Icon(Icons.Default.Fingerprint, contentDescription = null, tint = Color(0xFF0F172A))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Biometric Login", color = Color(0xFF0F172A), fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(if(isMobile) 24.dp else 48.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Authorized Access Only", color = Color(0xFF94A3B8), fontSize = 11.sp)
            Text("v1.0.0 Internal", color = Color(0xFF94A3B8), fontSize = 11.sp)
        }
    }
}
