package com.example.loginapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loginapp.ui.theme.LoginappTheme

enum class Screen {
    LOGIN, ITEM_LIST, ADD_ITEM
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginappTheme{
                AppController()
            }
        }
    }
}

@Composable
fun AppController(
    viewModel: ItemViewModel = viewModel()
) {
    var currentScreen by remember { mutableStateOf(Screen.LOGIN) }

    when (currentScreen) {
        Screen.LOGIN -> {
            LoginScreen(
                onLoginSuccess = { username ->
                    viewModel.setUsername(username)
                    currentScreen = Screen.ITEM_LIST
                }
            )
        }
        Screen.ITEM_LIST -> {
            ItemListScreen(
                viewModel = viewModel,
                onAddItemClick = {
                    currentScreen = Screen.ADD_ITEM
                }
            )
        }
        Screen.ADD_ITEM -> {
            AddItemScreen(
                viewModel = viewModel,
                onItemAdded = {
                    currentScreen = Screen.ITEM_LIST
                }
            )
        }
    }
}

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit
) {
    var usernameInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = usernameInput,
            onValueChange = {
                usernameInput = it
                isError = false
            },
            label = { Text("Nama Pengguna") },
            isError = isError,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = passwordInput,
            onValueChange = {
                passwordInput = it
                isError = false
            },
            label = { Text("Kata Sandi") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = isError && passwordInput.isEmpty(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val username = usernameInput.trim()
                val password = passwordInput.trim()
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    onLoginSuccess(username)
                } else {
                    isError = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginappTheme{
        LoginScreen(onLoginSuccess = {})
    }
}