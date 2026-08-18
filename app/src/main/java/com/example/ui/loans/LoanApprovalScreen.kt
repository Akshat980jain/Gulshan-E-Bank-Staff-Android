package com.example.ui.loans

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanApprovalScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Advanced Loan Underwriting", fontWeight = FontWeight.Bold) },
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
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(3) { index ->
                LoanCard(
                    index = index,
                    borrower = "Borrower ${index + 1}",
                    type = "Personal Loan",
                    amount = "₹ 50,000",
                    tenure = "12 Months",
                    rate = "10.5%"
                )
            }
        }
    }
}

@Composable
fun LoanCard(index: Int, borrower: String, type: String, amount: String, tenure: String, rate: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = borrower, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(text = type, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Amount", style = MaterialTheme.typography.labelMedium)
                    Text(amount, style = MaterialTheme.typography.bodyLarge)
                }
                Column {
                    Text("Credit Score", style = MaterialTheme.typography.labelMedium)
                    Text("74${index} (Experian)", style = MaterialTheme.typography.bodyLarge, color = Color(0xFF2E7D32))
                }
                Column {
                    Text("DTI Ratio", style = MaterialTheme.typography.labelMedium)
                    Text("3${index}%", style = MaterialTheme.typography.bodyLarge)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().background(Color(0xFFE0F2F1)).padding(8.dp)) {
                Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = Color(0xFF00695C), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Income Docs Verified (AI OCR Match)", color = Color(0xFF00695C), style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedButton(onClick = { /* Reject */ }, modifier = Modifier.weight(1f)) {
                    Text("Reject")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { /* Approve */ }, modifier = Modifier.weight(1f)) {
                    Text("Approve")
                }
            }
        }
    }
}
