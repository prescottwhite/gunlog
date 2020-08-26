package com.pgwhite.gunlog

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Gun::class), version = 1, exportSchema = false)
public abstract class GunRoomDatabase : RoomDatabase() {

    abstract fun gunDAO(): GunDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: GunRoomDatabase? = null

        fun getDatabase(
                context: Context,
                scope: CoroutineScope
            ): GunRoomDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GunRoomDatabase::class.java,
                    "gun_database"
                )
                    .addCallback(GunDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class GunDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.gunDAO())
                }
            }
        }

        suspend fun populateDatabase(gunDao: GunDAO) {
            // Delete all content here.
            //gunDao.deleteAll()

            // Add sample words.
//            var gun = Gun(0, "Glock", "19")
//            gunDao.insert(gun)
//            gun = Gun(0, "Remington", "870")
//            gunDao.insert(gun)
        }
    }
}