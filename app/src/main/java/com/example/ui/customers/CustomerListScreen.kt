package com.example.ui.customers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerListScreen(onBack: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCustomer by remember { mutableStateOf<String?>(null) }
    
    if (selectedCustomer != null) {
        CustomerDetailScreen(
            customerName = selectedCustomer!!,
            onBack = { selectedCustomer = null }
        )
        return
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Customer Directory", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF8F9FA),
                    titleContentColor = Color(0xFF1E293B),
                    navigationIconContentColor = Color(0xFF1E293B)
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search by Name, Email or ID") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )
            
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val customers = listOf(
                    "Eleanor Pena" to "VERIFIED",
                    "Wade Warren" to "VERIFIED",
                    "Jacob Jones" to "FROZEN",
                    "Courtney Henry" to "VERIFIED",
                    "Bessie Cooper" to "PENDING"
                )
                
                items(customers.size) { index ->
                    val customer = customers[index]
                    CustomerCard(
                        name = customer.first,
                        email = "${customer.first.lowercase().replace(" ", ".")}@example.com",
                        status = customer.second,
                        onClick = { selectedCustomer = customer.first }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomerDetailScreen(customerName: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text(customerName, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Flag */ }) {
                        Icon(Icons.Default.Flag, contentDescription = "Flag Account", tint = Color(0xFFEF4444))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2563EB),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF2563EB))
                        .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
                ) {
                    Column {
                        Text("Current Balance", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                        Text("$124,532.00", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Badge(containerColor = Color(0xFF10B981)) { Text("ACTIVE", modifier = Modifier.padding(6.dp)) }
                            Badge(containerColor = Color.White.copy(alpha = 0.2f)) { Text("KYC VERIFIED", color = Color.White, modifier = Modifier.padding(6.dp)) }
                        }
                    }
                }
            }
            
            item {
                PaddingValues(16.dp)
                Text(
                    "Transaction Ledger",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF1E293B),
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
                )
            }
            
            // Date Group: Today
            item {
                Text(
                    "Today, Aug 16",
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF64748B),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            item {
                TransactionItem(title = "Wire Transfer to AWS", type = "DEBIT", amount = "-$4,200.00", time = "14:32 PM")
            }
            item {
                TransactionItem(title = "Stripe Payout", type = "CREDIT", amount = "+$12,450.00", time = "09:15 AM")
            }
            
            // Date Group: Yesterday
            item {
                Text(
                    "Yesterday, Aug 15",
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF64748B),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            item {
                TransactionItem(title = "Salary Deposit", type = "CREDIT", amount = "+$8,500.00", time = "18:00 PM")
            }
            item {
                TransactionItem(title = "Starbucks", type = "DEBIT", amount = "-$12.50", time = "08:24 AM")
            }
            item {
                TransactionItem(title = "Uber Rides", type = "DEBIT", amount = "-$34.20", time = "07:10 AM")
            }
        }
    }
}

@Composable
fun TransactionItem(title: String, type: String, amount: String, time: String) {
    val isCredit = type == "CREDIT"
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (isCredit) Color(0xFF10B981).copy(alpha = 0.1f) else Color(0xFFEF4444).copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isCredit) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                    contentDescription = null,
                    tint = if (isCredit) Color(0xFF10B981) else Color(0xFFEF4444)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B), fontSize = 15.sp)
                Text(time, color = Color(0xFF64748B), fontSize = 12.sp)
            }
            Text(
                amount,
                fontWeight = FontWeight.Bold,
                color = if (isCredit) Color(0xFF10B981) else Color(0xFF1E293B),
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun CustomerCard(name: String, email: String, status: String, onClick: () -> Unit) {
    val statusColor = when (status) {
        "VERIFIED" -> Color(0xFF10B981)
        "FROZEN" -> Color(0xFFEF4444)
        else -> Color(0xFFF59E0B)
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2563EB).copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(name.take(1), fontWeight = FontWeight.Bold, color = Color(0xFF2563EB), fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Text(text = email, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF64748B))
                }
            }
            Badge(
                containerColor = statusColor.copy(alpha = 0.1f),
                contentColor = statusColor,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(status, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontWeight = FontWeight.Bold)
            }
        }
    }
}

