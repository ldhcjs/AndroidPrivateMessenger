package com.ldhcjs.androidprivatemessenger.db.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.ldhcjs.androidprivatemessenger.db.entity.ChatEntity

@Dao
interface ChatDao {
    @Query("SELECT * FROM ChatEntity ORDER BY id ASC")
    fun selectAllChat(): MutableList<ChatEntity>

    @Query("SELECT * FROM ChatEntity ORDER BY id DESC")
    fun selectAllChatAsync(): LiveData<MutableList<ChatEntity>>

    @Insert
    fun insert(chatEntity: ChatEntity)

    @Update
    fun update(chatEntity: ChatEntity)

    @Delete
    fun delete(chatEntity: ChatEntity)
}