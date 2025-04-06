package com.divya.imperativeassignment



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TransactionScreen(viewModel: TransactionViewModel, onLogout: () -> Unit) {
    val transactions = viewModel.transactions.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                viewModel.logout()
                onLogout()
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("Logout")
        }

        LazyColumn {
            items(transactions.size) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "ID: ${transactions[index].id}")
                        Text(text = "Amount: $${transactions[index].amount}")
                        Text(text = "Date: ${transactions[index].date}")
                        Text(text = "Description: ${transactions[index].description}")
                    }
                }
            }
        }
    }
}