package com.example.data.api

import com.example.data.model.DashboardCountsDto
import com.example.data.model.KycDocumentDto
import com.example.data.model.LoanDto
import com.example.data.model.LoginRequest
import com.example.data.model.LoginResponse
import com.example.data.model.SimpleResponse
import com.example.data.model.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface StaffApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("kyc/pending-count")
    suspend fun getPendingKycCount(): DashboardCountsDto

    @GET("loans/pending-count")
    suspend fun getPendingLoansCount(): DashboardCountsDto

    @GET("services/pending-count")
    suspend fun getPendingServicesCount(): DashboardCountsDto

    @GET("support/open-count")
    suspend fun getOpenSupportCount(): DashboardCountsDto
    
    @GET("auth/all-users")
    suspend fun getAllUsers(): List<UserDto>
    
    @POST("auth/unfreeze/{userId}")
    suspend fun unfreezeAccount(@Path("userId") userId: String): SimpleResponse
    
    @GET("kyc/all")
    suspend fun getAllKyc(): List<KycDocumentDto>
    
    @PATCH("kyc/approve/{uid}")
    suspend fun approveKyc(@Path("uid") uid: String): SimpleResponse
    
    @PATCH("kyc/reject/{uid}")
    suspend fun rejectKyc(@Path("uid") uid: String): SimpleResponse
    
    @GET("loans/all")
    suspend fun getAllLoans(): List<LoanDto>
    
    @PATCH("loans/approve/{loanId}")
    suspend fun approveLoan(@Path("loanId") loanId: String): SimpleResponse
    
    @PATCH("loans/reject/{loanId}")
    suspend fun rejectLoan(@Path("loanId") loanId: String): SimpleResponse
}
