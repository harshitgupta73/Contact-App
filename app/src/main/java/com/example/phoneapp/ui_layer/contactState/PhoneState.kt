package com.example.phoneapp.ui_layer.contactState

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.phoneapp.data_layer.tables.ContactTable

data class PhoneState(
    val contactList: List<ContactTable> = emptyList(),
    val id: MutableState<Int?> = mutableStateOf(null),
    val phonenumber: MutableState<String> = mutableStateOf(""),
    val name: MutableState<String> = mutableStateOf(""),
    val email: MutableState<String> = mutableStateOf(""),
    val dob: MutableState<String> = mutableStateOf(""),
    val image : MutableState<ByteArray?> = mutableStateOf(null)
)