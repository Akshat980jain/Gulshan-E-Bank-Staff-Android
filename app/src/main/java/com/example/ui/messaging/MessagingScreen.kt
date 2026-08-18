package com.example.ui.messaging

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagingScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Secure Messaging Hub", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Icon(Icons.Default.Lock, contentDescription = "Encrypted", modifier = Modifier.padding(end = 16.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F172A),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                MessageThread("VIP - Elon Musk", "Can you increase my daily wire limit?", "10:45 AM", 1)
                MessageThread("KYC Follow-up - Sara", "I uploaded my passport as requested.", "Yesterday", 0)
                MessageThread("Wealth Mgmt - Bruce", "Let's review the portfolio performance.", "Mon", 2)
            }
        }
    }
}

@Composable
fun MessageThread(sender: String, preview: String, time: String, unreadCount: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(sender, fontWeight = FontWeight.Bold)
                    Text(time, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(preview, color = Color.DarkGray, maxLines = 1, style = MaterialTheme.typography.bodyMedium)
            }
            if (unreadCount > 0) {
                Spacer(modifier = Modifier.width(16.dp))
                Badge { Text(unreadCount.toString()) }
            }
        }
    }
}
