package com.gahee.hotchoco

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marsh_mallow")
data class MarshMallow(
    @PrimaryKey(autoGenerate = false)
    val date : String,
    val content : String
)