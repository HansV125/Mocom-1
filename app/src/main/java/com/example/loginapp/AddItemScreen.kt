package com.example.loginapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddItemScreen(
    viewModel: ItemViewModel,
    onItemAdded: () -> Unit
) {
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    var isNameError by remember { mutableStateOf(false) }
    var isQuantityError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Tambah Barang",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = itemName,
            onValueChange = {
                itemName = it
                isNameError = false
            },
            label = { Text("Nama Barang") },
            isError = isNameError,
            supportingText = { if (isNameError) Text("Nama barang tidak boleh kosong") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = itemQuantity,
            onValueChange = {
                itemQuantity = it.filter { char -> char.isDigit() }
                isQuantityError = false
            },
            label = { Text("Banyaknya Barang") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isQuantityError,
            supportingText = { if (isQuantityError) Text("Harus Angka Valid") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                val name = itemName.trim()
                val quantity = itemQuantity.toIntOrNull()

                val validName = name.isNotEmpty()
                val validQuantity = quantity != null && quantity > 0

                isNameError = !validName
                isQuantityError = !validQuantity

                if (validName && validQuantity) {
                    val newItem = Item(name = name, quantity = quantity!!)
                    viewModel.addItem(newItem)
                    onItemAdded()
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Simpan Barang")
        }
    }
}

