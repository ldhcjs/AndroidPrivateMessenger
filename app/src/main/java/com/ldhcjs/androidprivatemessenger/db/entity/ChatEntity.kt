package com.ldhcjs.androidprivatemessenger.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatEntity(
    val name: String,
    val title: String,
    val content: String,
    val profile: String
    ) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}