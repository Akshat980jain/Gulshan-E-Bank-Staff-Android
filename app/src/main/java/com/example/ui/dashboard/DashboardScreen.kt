package com.example.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToCustomers: () -> Unit,
    onLogout: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val primaryBlue = Color(0xFF2563EB)
    val bgLight = Color(0xFFF8F9FA)
    var currentTab by remember { mutableIntStateOf(0) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var isBalanceVisible by remember { mutableStateOf(true) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Confirm Logout") },
            text = { Text("Are you sure you want to sign out of the staff workstation?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    }
                ) {
                    Text("Logout", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        containerColor = bgLight,
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                NavigationBarItem(selected = currentTab == 0, onClick = { currentTab = 0 }, icon = { Icon(Icons.Default.Dashboard, null) }, label = { Text("Home") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = primaryBlue, selectedTextColor = primaryBlue, indicatorColor = primaryBlue.copy(alpha=0.1f)))
                NavigationBarItem(selected = currentTab == 1, onClick = { currentTab = 1 }, icon = { Icon(Icons.Default.People, null) }, label = { Text("Customers") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = primaryBlue, selectedTextColor = primaryBlue, indicatorColor = primaryBlue.copy(alpha=0.1f)))
                NavigationBarItem(selected = currentTab == 2, onClick = { currentTab = 2 }, icon = { Icon(Icons.Default.FactCheck, null) }, label = { Text("KYC") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = primaryBlue, selectedTextColor = primaryBlue, indicatorColor = primaryBlue.copy(alpha=0.1f)))
                NavigationBarItem(selected = currentTab == 3, onClick = { currentTab = 3 }, icon = { Icon(Icons.Default.AssignmentTurnedIn, null) }, label = { Text("Loans") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = primaryBlue, selectedTextColor = primaryBlue, indicatorColor = primaryBlue.copy(alpha=0.1f)))
                NavigationBarItem(selected = currentTab == 4, onClick = { currentTab = 4 }, icon = { Icon(Icons.Default.AdminPanelSettings, null) }, label = { Text("Admin") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = primaryBlue, selectedTextColor = primaryBlue, indicatorColor = primaryBlue.copy(alpha=0.1f)))
            }
        },
        floatingActionButton = {
            if (currentTab == 0) {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.refreshData() },
                    icon = { Icon(Icons.Default.Sync, null) },
                    text = { Text("Refresh Sync") },
                    containerColor = primaryBlue,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(24.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (currentTab) {
                0 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(primaryBlue), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Gulshan Staff Portal", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                        Text("Operational Command Center", style = MaterialTheme.typography.bodySmall, color = Color(0xFF64748B))
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { showLogoutDialog = true }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout", tint = Color(0xFF1E293B))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFDBEAFE)), contentAlignment = Alignment.Center) {
                        Text("A", color = primaryBlue, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Hero Card
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = primaryBlue),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Current Account Total", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                        IconButton(onClick = { isBalanceVisible = !isBalanceVisible }, modifier = Modifier.size(24.dp)) {
                            Icon(
                                imageVector = if (isBalanceVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle Visibility",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isBalanceVisible) "$12,450,000" else "••••••••••••",
                            fontSize = 32.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Surface(color = Color(0xFF10B981).copy(alpha=0.2f), shape = RoundedCornerShape(8.dp)) {
                            Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.TrendingUp, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("+12.4%", color = Color(0xFF34D399), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    HorizontalDivider(color = Color.White.copy(alpha = 0.2f), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("KYC Docs", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("${state.pendingKyc}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Column {
                            Text("Loan Apps", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("${state.pendingLoans}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Column {
                            Text("Tickets", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("${state.openSupport}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Operational Overview", modifier = Modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            
            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OverviewCard(modifier = Modifier.weight(1f), title = "Pending KYC", value = "${state.pendingKyc}", trend = "+8.2%", trendUp = true, icon = Icons.Default.ArrowDownward, onClick = { currentTab = 2 })
                    OverviewCard(modifier = Modifier.weight(1f), title = "Loan Reviews", value = "${state.pendingLoans}", trend = "-4.1%", trendUp = false, icon = Icons.Default.ArrowUpward, onClick = { currentTab = 3 })
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OverviewCard(modifier = Modifier.weight(1f), title = "Service Reqs", value = "${state.pendingServices}", trend = "+12.5%", trendUp = true, icon = Icons.Default.Savings, onClick = { currentTab = 4 })
                    OverviewCard(modifier = Modifier.weight(1f), title = "Support Tickets", value = "${state.openSupport}", trend = "+5.8%", trendUp = true, icon = Icons.Default.TrendingUp, onClick = { currentTab = 4 })
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))

            Text("Workstation Status", modifier = Modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            Text("Microservices & database health", modifier = Modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.bodySmall, color = Color(0xFF64748B))

            Spacer(modifier = Modifier.height(16.dp))
            
            Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                StatusItem("Staff Backend API", "Port 3002 • Active", Icons.Default.Dns, Color(0xFF10B981))
                StatusItem("MongoDB Database", "Cluster Connected", Icons.Default.Storage, Color(0xFF10B981))
                StatusItem("Authentication", "Secure & Active", Icons.Default.Security, Color(0xFF10B981))
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text("Quick Workflows", modifier = Modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceAround) {
                QuickActionBtn(Icons.Default.People, "Customers", onClick = { currentTab = 1 })
                QuickActionBtn(Icons.Default.VerifiedUser, "KYC", onClick = { currentTab = 2 })
                QuickActionBtn(Icons.Default.Gavel, "Loans", onClick = { currentTab = 3 })
                QuickActionBtn(Icons.Default.AccountBalance, "Vault", onClick = { currentTab = 8 })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceAround) {
                QuickActionBtn(Icons.Default.Warning, "Fraud", onClick = { currentTab = 5 })
                QuickActionBtn(Icons.Default.HeadsetMic, "Support", onClick = { currentTab = 6 })
                QuickActionBtn(Icons.Default.Lock, "Messages", onClick = { currentTab = 7 })
                QuickActionBtn(Icons.Default.AdminPanelSettings, "Audit", onClick = { currentTab = 4 })
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text("Recent Activity", modifier = Modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ActivityItem("Approved Loan #4920", "10:42 AM", Icons.Default.CheckCircle, Color(0xFF10B981))
                ActivityItem("KYC Verified: Jane Doe", "09:15 AM", Icons.Default.VerifiedUser, Color(0xFF2563EB))
                ActivityItem("System Audit Completed", "08:00 AM", Icons.Default.FactCheck, Color(0xFF64748B))
            }

            Spacer(modifier = Modifier.height(100.dp)) // padding for FAB
                    }
                }
                1 -> { com.example.ui.customers.CustomerListScreen(onBack = { currentTab = 0 }) }
                2 -> { com.example.ui.kyc.KycReviewScreen(onBack = { currentTab = 0 }) }
                3 -> { com.example.ui.loans.LoanApprovalScreen(onBack = { currentTab = 0 }) }
                4 -> { com.example.ui.admin.AdminPanelScreen(onBack = { currentTab = 0 }) }
                5 -> { com.example.ui.fraud.FraudMonitoringScreen(onBack = { currentTab = 0 }) }
                6 -> { com.example.ui.support.SupportDeskScreen(onBack = { currentTab = 0 }) }
                7 -> { com.example.ui.messaging.MessagingScreen(onBack = { currentTab = 0 }) }
                8 -> { com.example.ui.vault.VaultManagementScreen(onBack = { currentTab = 0 }) }
            }
        }
    }
}

@Composable
fun StatusItem(title: String, subtitle: String, icon: ImageVector, statusColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(subtitle, color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
            }
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(statusColor))
        }
    }
}

@Composable
fun OverviewCard(modifier: Modifier, title: String, value: String, trend: String, trendUp: Boolean, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = modifier.aspectRatio(1f).clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFEFF6FF)), contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = Color(0xFF2563EB))
                }
                Surface(color = if (trendUp) Color(0xFFD1FAE5) else Color(0xFFFFE4E6), shape = RoundedCornerShape(8.dp)) {
                    Text(trend, color = if (trendUp) Color(0xFF059669) else Color(0xFFE11D48), modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
            Column {
                Text(title, color = Color(0xFF64748B), fontSize = 14.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            }
        }
    }
}

@Composable
fun QuickActionBtn(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }) {
        Box(
            modifier = Modifier.size(64.dp).clip(CircleShape).background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B))
    }
}

@Composable
fun ActivityItem(title: String, time: String, icon: ImageVector, iconColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(iconColor.copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = iconColor)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color(0xFF1E293B), fontWeight = FontWeight.Medium, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(time, color = Color(0xFF94A3B8), fontSize = 12.sp)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color(0xFFCBD5E1))
        }
    }
}
