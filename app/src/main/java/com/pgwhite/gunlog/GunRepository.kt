package com.pgwhite.gunlog

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class GunRepository(private val gunDAO: GunDAO) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allGuns: LiveData<List<Gun>> = gunDAO.getAlphabetizedWords()

    suspend fun insert(gun: Gun) {
        gunDAO.insert(gun)
    }
}