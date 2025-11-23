package com.example.loginapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class Item(val name: String, val quantity: Int)
class ItemViewModel : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    val items = mutableListOf<Item>()

    fun addItem(item: Item) {
        items.add(item)
    }
    init {}
    fun setUsername(name: String) {
        _username.value = name
    }
}


