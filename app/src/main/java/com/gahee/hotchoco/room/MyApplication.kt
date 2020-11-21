package com.gahee.hotchoco.room

import android.app.Application
import com.gahee.hotchoco.room.MarshMallowDatabase.Companion.getDatabase

class MyApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { getDatabase(this) }
    val repository by lazy { MarshRepository(database.marshMallowDao()) }
}
