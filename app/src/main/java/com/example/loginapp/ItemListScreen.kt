package com.example.loginapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
@Composable
fun ItemListScreen(
    viewModel: ItemViewModel,
    onAddItemClick: () -> Unit
) {
    val username by viewModel.username.observeAsState(initial = "Pengguna")
    val itemList = viewModel.items

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddItemClick) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Barang")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Selamat Datang, $username!",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ItemTableHeader()

            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface, thickness = 1.dp)
            if (itemList.isEmpty()) {
                Text(
                    text = "Belum ada barang.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(itemList) { item ->
                        ItemRow(item = item)
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun ItemTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 8.dp),
    ) {
        Text(
            text = "Nama Barang",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(3f).padding(start = 8.dp)
        )
        Text(
            text = "Jumlah",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f).padding(end = 8.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun ItemRow(item: Item) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name,
            modifier = Modifier.weight(3f),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = item.quantity.toString(),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}