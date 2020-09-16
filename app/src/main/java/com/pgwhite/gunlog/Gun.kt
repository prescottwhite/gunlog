package com.pgwhite.gunlog

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gun_table")
class Gun(@PrimaryKey(autoGenerate = true) val id: Int,
          @ColumnInfo(name = "mfr") val mfr: String,
          @ColumnInfo(name = "model") val model: String,
          @ColumnInfo(name = "rounds_total") var rounds_total: Int,
          @ColumnInfo(name = "recoil_spring_bool") var recoil_spring_bool: Boolean = false,
          @ColumnInfo(name = "recoil_spring_rounds") var recoil_spring_rounds: Int = 0) {
}