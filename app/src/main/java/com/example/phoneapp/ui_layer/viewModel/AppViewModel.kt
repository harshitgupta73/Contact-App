package com.example.phoneapp.ui_layer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phoneapp.data_layer.repo.AppRepo
import com.example.phoneapp.data_layer.tables.ContactTable
import com.example.phoneapp.data_layer.tables.FavouriteTable
import com.example.phoneapp.data_layer.tables.RecentTable
import com.example.phoneapp.ui_layer.contactState.FavouriteState
import com.example.phoneapp.ui_layer.contactState.PhoneState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repo: AppRepo) : ViewModel() {
    //Contact
    private val contactList = repo.getAllContacts()
        .stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(), emptyList())

    private val state = MutableStateFlow(PhoneState())
    val contactState = combine(
        contactList,
        state
    ) { contactList, state -> state.copy(contactList = contactList) }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        PhoneState()
    )

    fun upsertContact() {
        viewModelScope.launch {
            repo.upsertContact(
                ContactTable(
                    id = state.value.id.value,
                    name = state.value.name.value,
                    phoneNumber = state.value.phonenumber.value,
                    email = state.value.email.value,
                    dob = state.value.dob.value,
                    image = state.value.image.value
                )
            )
        }

    }

    fun deleteContact(contactTable: ContactTable) {
        viewModelScope.launch {
            repo.deleteContact(
                contactTable
            )
        }
    }

    //FavouriteTable
    private val favouriteList = repo.getAllFavourites()
        .stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(), emptyList())
    private val _state= MutableStateFlow(FavouriteState())

    val favouriteState = combine(
        favouriteList,
        _state
    ){
        favouriteList,state -> state.copy(
            favouriteList = favouriteList
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FavouriteState())


    fun upsertFavContact(contact: ContactTable) {
        viewModelScope.launch {
            repo.insertFavouriteContact(FavouriteTable(
                id = contact.id,
                name = contact.name,
                email = contact.email,
                phoneNumber = contact.phoneNumber,
                dob = contact.dob,
                image = contact.image
            ))
        }

    }

    fun deleteFavContact(favourite: FavouriteTable) {
        viewModelScope.launch {
            repo.deleteFavouriteContact(favourite)
        }
    }

    val recentContactList = repo.getLastAccessedContact().stateIn(
        viewModelScope, started = SharingStarted.WhileSubscribed(), emptyList()
    )

    fun saveToRecent(contact: ContactTable) {
        viewModelScope.launch {
            val recentContact = RecentTable(
                id = contact.id,
                name = contact.name,
                phoneNumber = contact.phoneNumber,
                lastAccessed = System.currentTimeMillis(),
                image = contact.image
            )
            repo.insertRecentContact(recentContact)
        }
    }
    fun saveToRecentPhone(phoneNumber : String) {

        viewModelScope.launch {
            val recentContact = RecentTable(
                id = 0,
                name = "Contact $phoneNumber",
                phoneNumber = phoneNumber,
                lastAccessed = System.currentTimeMillis(),
                image = null
            )
            repo.insertRecentContact(recentContact)
        }
    }


}