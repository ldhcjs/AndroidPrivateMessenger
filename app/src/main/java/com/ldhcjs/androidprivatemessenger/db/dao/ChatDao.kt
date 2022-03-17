package com.ldhcjs.androidprivatemessenger.db.dao

import androidx.room.*
import com.ldhcjs.androidprivatemessenger.db.entity.ChatEntity

@Dao
interface ChatDao {
    @Insert
    fun insert(chatEntity: ChatEntity)

    @Update
    fun update(chatEntity: ChatEntity)

    @Delete
    fun delete(chatEntity: ChatEntity)
}