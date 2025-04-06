package com.divya.imperativeassignment



class TransactionRepository(
    private val apiService: ApiService,
    private val securePrefs: SecurePrefs
) {
    suspend fun login(username: String, password: String): String {
        val response = apiService.login(LoginRequest(username, password))
        securePrefs.saveToken(response.token)
        return response.token
    }

    suspend fun getTransactions() = apiService.getTransactions()

    fun getToken() = securePrefs.getToken()
    fun clearToken() = securePrefs.clearToken()
}