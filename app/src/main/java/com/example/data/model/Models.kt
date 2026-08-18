package com.example.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    val message: String,
    val token: String,
    val user: UserDto
)

@JsonClass(generateAdapter = true)
data class UserDto(
    val id: String,
    val customerId: String? = null,
    val name: String,
    val email: String,
    val role: String,
    val kycStatus: String = "unverified",
    val accountFrozen: Boolean = false
)

@JsonClass(generateAdapter = true)
data class KycDocumentDto(
    val id: String,
    val userId: String,
    val userName: String? = null,
    val status: String = "pending",
    val documentsSubmitted: Int = 0,
    val progress: Int = 0,
    val submittedAt: String? = null
)

@JsonClass(generateAdapter = true)
data class LoanDto(
    val id: String,
    val userId: String,
    val loanType: String,
    val amount: Double,
    val tenure: Int,
    val interestRate: Double,
    val purpose: String? = null,
    val status: String = "pending"
)

@JsonClass(generateAdapter = true)
data class LoginRequest(
    val email: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class DashboardCountsDto(
    val count: Int
)

@JsonClass(generateAdapter = true)
data class SimpleResponse(
    val message: String
)
