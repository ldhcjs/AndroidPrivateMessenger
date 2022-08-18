package com.ldhcjs.androidprivatemessenger.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatEntity(
    val title: String,
    val msg: String,
    val name: String,
    val time: String,
    val who: String,
    val group: String
    ) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}