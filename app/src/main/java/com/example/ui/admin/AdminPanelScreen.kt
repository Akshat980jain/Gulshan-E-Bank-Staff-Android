package com.example.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanelScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Panel") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("System Health", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Core Services: Online", color = androidx.compose.ui.graphics.Color(0xFF2E7D32))
                        Text("Database: Connected", color = androidx.compose.ui.graphics.Color(0xFF2E7D32))
                        Text("Storage: 68% Used")
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("AML/KYC Compliance Ledger", style = MaterialTheme.typography.titleLarge)
            }
            items(5) { index ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Action: ${if (index % 2 == 0) "Approved KYC Document" else "Flagged Suspicious Wire"}", style = MaterialTheme.typography.titleMedium, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                            Text("ID: #A${8000+index}", color = androidx.compose.ui.graphics.Color.Gray)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Staff Member: Jane_Admin_${index + 1}", style = MaterialTheme.typography.bodyMedium, color = androidx.compose.ui.graphics.Color(0xFF2563EB))
                        Text("Target Acct: ${if (index % 2 == 0) "John Doe (Verified)" else "Offshore LLC (Frozen)"}", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Timestamp: 2026-08-16 10:0${index} AM", style = MaterialTheme.typography.labelSmall, color = androidx.compose.ui.graphics.Color.DarkGray)
                    }
                }
            }
        }
    }
}
