package com.example.ui.kyc

import android.app.Activity
import android.content.pm.ApplicationInfo
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

data class KycApplicant(val name: String, val submissionDate: String, val idType: String, val nationality: String)

val dummyApplicants = listOf(
    KycApplicant("Eleanor Pena", "Aug 16, 2026, 09:12 AM", "Aadhaar Card", "India"),
    KycApplicant("Wade Warren", "Aug 15, 2026, 04:30 PM", "PAN Card", "India"),
    KycApplicant("Jacob Jones", "Aug 14, 2026, 11:45 AM", "Voter ID", "India"),
    KycApplicant("Guy Hawkins", "Aug 14, 2026, 09:20 AM", "Driving License", "India"),
    KycApplicant("Bessie Cooper", "Aug 13, 2026, 02:15 PM", "Passport", "United States")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KycReviewScreen(onBack: () -> Unit) {
    var selectedApplicant by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf<KycApplicant?>(null) }
    
    if (selectedApplicant == null) {
        KycListScreen(
            onBack = onBack,
            onApplicantSelected = { selectedApplicant = it }
        )
    } else {
        KycDetailScreen(
            applicant = selectedApplicant!!,
            onBack = { selectedApplicant = null }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KycListScreen(onBack: () -> Unit, onApplicantSelected: (KycApplicant) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pending KYC Queue", fontWeight = FontWeight.Bold) },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(dummyApplicants.size) { index ->
                val applicant = dummyApplicants[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onApplicantSelected(applicant) },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2563EB).copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Face, contentDescription = null, tint = Color(0xFF2563EB))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(applicant.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1E293B))
                            Text("Submitted: ${applicant.submissionDate}", color = Color.Gray, fontSize = 12.sp)
                        }
                        Badge(containerColor = Color(0xFFF59E0B)) {
                            Text("PENDING", modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KycDetailScreen(applicant: KycApplicant, onBack: () -> Unit) {
    var expandedImageTitle by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val isDebug = (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0

    DisposableEffect(Unit) {
        val window = (context as? Activity)?.window
        // Allow screenshots in development mode
        if (!isDebug) {
            window?.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
        onDispose {
            if (!isDebug) {
                window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Document Review", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF0F172A))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF0F172A),
                    navigationIconContentColor = Color(0xFF0F172A)
                )
            )
        },
        containerColor = Color(0xFFF1F5F9) // Sleeker light gray background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (!isDebug) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Secure View Active: Screenshots disabled.",
                                color = Color(0xFFDC2626),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            item {
                Text("Applicant Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF334155))
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Full Legal Name", color = Color(0xFF94A3B8), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(applicant.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF0F172A))
                            }
                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                                Text("Nationality", color = Color(0xFF94A3B8), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(applicant.nationality, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF0F172A))
                            }
                        }
                        Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFF1F5F9))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("ID Type", color = Color(0xFF94A3B8), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(applicant.idType, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF0F172A))
                            }
                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                                Text("Submitted At", color = Color(0xFF94A3B8), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(applicant.submissionDate, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF0F172A))
                            }
                        }
                    }
                }
            }

            item {
                Text("Document Scans", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF334155))
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // ID Front Placeholder
                    Column(modifier = Modifier.weight(1f)) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(3f / 4f)
                                .clickable { expandedImageTitle = "${applicant.idType} Scan" },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                            border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFE2E8F0))
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                // Simulate a gradient photo placeholder
                                Box(modifier = Modifier.fillMaxSize().background(androidx.compose.ui.graphics.Brush.linearGradient(listOf(Color(0xFFE2E8F0), Color(0xFFCBD5E1)))))
                                Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f))) // Dark overlay for contrast
                                
                                Column(
                                    modifier = Modifier.fillMaxSize(), 
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(Icons.Default.AssignmentInd, contentDescription = "ID Front", modifier = Modifier.size(32.dp), tint = Color.White)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("${applicant.idType} Scan", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                                }
                            }
                        }
                    }
                    
                    // Selfie Placeholder
                    Column(modifier = Modifier.weight(1f)) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(3f / 4f)
                                .clickable { expandedImageTitle = "Liveness Check" },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                            border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFE2E8F0))
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Box(modifier = Modifier.fillMaxSize().background(androidx.compose.ui.graphics.Brush.linearGradient(listOf(Color(0xFFE2E8F0), Color(0xFFCBD5E1)))))
                                Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f))) // Dark overlay for contrast
                                
                                Column(
                                    modifier = Modifier.fillMaxSize(), 
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(Icons.Default.Face, contentDescription = "Selfie", modifier = Modifier.size(32.dp), tint = Color.White)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Liveness Check", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBBF7D0)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(56.dp).clip(CircleShape).background(Color(0xFFDCFCE7)),
                            contentAlignment = Alignment.Center
                        ) {
                            // Pseudo circular progress
                            CircularProgressIndicator(progress = { 0.98f }, modifier = Modifier.size(48.dp), color = Color(0xFF16A34A), trackColor = Color(0xFFBBF7D0), strokeWidth = 4.dp)
                            Text("98%", color = Color(0xFF166534), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Facial Match Verified", color = Color(0xFF166534), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("No discrepancies found in OCR text.", color = Color(0xFF15803D), fontSize = 13.sp)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedButton(
                        onClick = { onBack() },
                        modifier = Modifier.weight(1f).height(60.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF0F172A))
                    ) {
                        Text("Reject", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { onBack() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                        modifier = Modifier.weight(2f).height(60.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                    ) {
                        Text("Approve KYC", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        if (expandedImageTitle != null) {
            Dialog(
                onDismissRequest = { expandedImageTitle = null },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.9f))) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = expandedImageTitle ?: "",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .aspectRatio(3f / 4f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(androidx.compose.ui.graphics.Brush.linearGradient(listOf(Color(0xFFE2E8F0), Color(0xFFCBD5E1))))
                        ) {
                            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)))
                            Icon(
                                if (expandedImageTitle == "Liveness Check") Icons.Default.Face else Icons.Default.AssignmentInd,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(80.dp).align(Alignment.Center)
                            )
                        }
                    }
                    IconButton(
                        onClick = { expandedImageTitle = null },
                        modifier = Modifier.align(Alignment.TopEnd).padding(16.dp).background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }
            }
        }
    }
}
