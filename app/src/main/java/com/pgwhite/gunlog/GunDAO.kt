package com.pgwhite.gunlog

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GunDAO {
    @Query("SELECT * from gun_table ORDER BY mfr ASC")
    fun getAlphabetizedWords(): LiveData<List<Gun>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gun: Gun)

    @Query("DELETE FROM gun_table")
    suspend fun deleteAll()
}