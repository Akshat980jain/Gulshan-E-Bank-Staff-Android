package com.example.ui.vault

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaultManagementScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Vault Management", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF10B981),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.AccountBalance, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color(0xFF10B981))
            Spacer(modifier = Modifier.height(24.dp))
            Text("Branch Currency Reserves", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(32.dp))
            
            CircularProgressIndicator(
                progress = { 0.45f },
                modifier = Modifier.size(150.dp),
                color = Color(0xFF10B981),
                strokeWidth = 12.dp,
                trackColor = Color.LightGray
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            Text("\$450,000", fontSize = 42.sp, fontWeight = FontWeight.Bold)
            Text("Current Cash in Vault", color = Color.Gray)
            
            Spacer(modifier = Modifier.height(16.dp))
            Text("Target Reserve: \$1,000,000", color = Color(0xFF10B981), fontWeight = FontWeight.Medium)
            
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
            ) {
                Text("Order Brinks Cash Delivery", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
