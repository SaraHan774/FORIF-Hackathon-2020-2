package com.gahee.hotchoco.room

import androidx.annotation.WorkerThread
import com.gahee.hotchoco.MarshMallow
import kotlinx.coroutines.flow.Flow


class MarshRepository(private val marshMallowDao: MarshMallowDao) {

    val allMarshMallows: Flow<List<MarshMallow>> = marshMallowDao.getMarshMallows()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(marshMallow: MarshMallow) {
        marshMallowDao.insert(marshMallow)
    }
}
