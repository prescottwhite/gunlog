package com.pgwhite.gunlog

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GunDAO {
    @Query("SELECT * from gun_table ORDER BY mfr ASC")
    fun getAlphabetizedWords(): LiveData<List<Gun>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gun: Gun)

    @Delete
    fun delete(gun: Gun)

    @Query("DELETE FROM gun_table")
    suspend fun deleteAll()
}