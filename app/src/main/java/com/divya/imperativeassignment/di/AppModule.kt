package com.divya.imperativeassignment.di

import android.content.Context
import com.divya.imperativeassignment.Data.SecurePrefs
import com.divya.imperativeassignment.domain.TransactionRepository
import com.divya.imperativeassignment.domain.TransactionUseCase
import com.divya.imperativeassignment.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSecurePrefs(@ApplicationContext context: Context): SecurePrefs {
        return SecurePrefs(context)
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val okHttpClient = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .baseUrl("https://api.prepstripe.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService, securePrefs: SecurePrefs): TransactionRepository {
        return TransactionRepository(apiService, securePrefs)
    }

    @Provides
    @Singleton
    fun provideTransactionUseCase(repository: TransactionRepository): TransactionUseCase {
        return TransactionUseCase(repository)
    }
}