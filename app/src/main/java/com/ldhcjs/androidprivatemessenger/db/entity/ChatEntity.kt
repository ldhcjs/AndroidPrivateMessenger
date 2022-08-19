package com.ldhcjs.androidprivatemessenger.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatEntity(
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "msg") val msg: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "time") val time: String?,
    @ColumnInfo(name = "who") val who: String?,
    @ColumnInfo(name = "group") val group: String?
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}