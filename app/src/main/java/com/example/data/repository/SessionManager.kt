package com.example.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "staff_settings")

class SessionManager(private val context: Context) {
    
    companion object {
        val TOKEN_KEY = stringPreferencesKey("jwt_token")
        val USER_ROLE_KEY = stringPreferencesKey("user_role")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
    }

    val tokenFlow: Flow<String?> = context.dataStore.data.map { it[TOKEN_KEY] }
    val roleFlow: Flow<String?> = context.dataStore.data.map { it[USER_ROLE_KEY] }
    val userNameFlow: Flow<String?> = context.dataStore.data.map { it[USER_NAME_KEY] }
    val userEmailFlow: Flow<String?> = context.dataStore.data.map { it[USER_EMAIL_KEY] }

    suspend fun saveSession(token: String, role: String, name: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[USER_ROLE_KEY] = role
            prefs[USER_NAME_KEY] = name
            prefs[USER_EMAIL_KEY] = email
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
