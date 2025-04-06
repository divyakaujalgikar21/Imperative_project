package com.divya.imperativeassignment

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()
        val loginViewModel: LoginViewModel = hiltViewModel()

        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginSuccess = {
                        if (loginViewModel.isBiometricAvailable(this@MainActivity)) {
                            showBiometricPrompt(loginViewModel, navController)
                        } else {
                            navController.navigate("transactions") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                )
            }
            composable("transactions") {
                val transactionViewModel: TransactionViewModel = hiltViewModel()
                TransactionScreen(
                    viewModel = transactionViewModel,
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo("transactions") { inclusive = true }
                        }
                    }
                )
            }
        }
    }

    private fun showBiometricPrompt(viewModel : LoginViewModel, navController: androidx.navigation.NavController) {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this@MainActivity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                navController.navigate("transactions") {
                    popUpTo("login") { inclusive = true }
                }
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}