package com.example.ui.fraud

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FraudMonitoringScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fraud Alerts Queue", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEF4444),
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
                Text("High Risk Activity", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            items(3) { index ->
                FraudAlertCard(index)
            }
        }
    }
}

@Composable
fun FraudAlertCard(index: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFEF4444).copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFEF4444))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(if (index == 0) "Unusual Login Location" else "Massive Withdrawal", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(if (index == 0) "IP: 185.12.x.x (Moscow) • Client: John Doe" else "Amount: \$45,000 • Client: Alice Smith", color = Color.Gray, fontSize = 14.sp)
                }
                Text("Score: 9${8-index}", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { /* Freeze */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Freeze Account")
                }
                OutlinedButton(
                    onClick = { /* Approve */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Mark Safe")
                }
            }
        }
    }
}
