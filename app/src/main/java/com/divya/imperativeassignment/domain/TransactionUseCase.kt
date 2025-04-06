package com.divya.imperativeassignment.domain

class TransactionUseCase(private val repository: TransactionRepository) {
    suspend fun login(username: String, password: String) = repository.login(username, password)
    suspend fun getTransactions() = repository.getTransactions()
    fun getToken() = repository.getToken()
    fun logout() = repository.clearToken()
}