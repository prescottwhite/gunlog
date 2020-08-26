package com.pgwhite.gunlog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GunViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GunRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allGuns: LiveData<List<Gun>>

    init {
        val gunsDAO = GunRoomDatabase.getDatabase(application, viewModelScope).gunDAO()
        repository = GunRepository(gunsDAO)
        allGuns = repository.allGuns
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(gun: Gun) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(gun)
    }
}