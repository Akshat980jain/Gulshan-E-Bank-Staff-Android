package com.example.ui.support

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportDeskScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Support Ticketing", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2563EB),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                TicketCard("TKT-8992", "Lost Credit Card", "Customer reported card lost in transit. Needs block and reissue.", "High")
                Spacer(modifier = Modifier.height(16.dp))
                TicketCard("TKT-8993", "Address Change Verification", "Uploaded new utility bill. Awaiting manual confirmation.", "Medium")
                Spacer(modifier = Modifier.height(16.dp))
                TicketCard("TKT-8994", "Disputed Charge", "Unrecognized Amazon charge for \$199.99.", "Medium")
            }
        }
    }
}

@Composable
fun TicketCard(id: String, title: String, desc: String, priority: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(id, color = Color.Gray, fontWeight = FontWeight.Bold)
                Badge(containerColor = if (priority == "High") Color.Red else Color(0xFFF59E0B)) {
                    Text(priority, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(desc, style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Take Action")
            }
        }
    }
}
