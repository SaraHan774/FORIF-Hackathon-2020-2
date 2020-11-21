package com.gahee.hotchoco.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gahee.hotchoco.model.MarshMallow

@Database(entities = [MarshMallow::class], version = 1, exportSchema = false)
public abstract class MarshMallowDatabase : RoomDatabase() {

   abstract fun marshMallowDao(): MarshMallowDao

   companion object {
        @Volatile
        private var INSTANCE: MarshMallowDatabase? = null

        fun getDatabase(context: Context): MarshMallowDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MarshMallowDatabase::class.java,
                        "marsh_mallow_db"
                    ).build()
                INSTANCE = instance
                instance
            }
        }
   }
}
