package com.ldhcjs.androidprivatemessenger.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ldhcjs.androidprivatemessenger.db.dao.ChatDao
import com.ldhcjs.androidprivatemessenger.db.entity.ChatEntity

@Database(entities = [ChatEntity::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao

    // Singleton 객체 구현
    companion object {
        private var instance: ChatDatabase? = null

        @Synchronized
        fun getInstance(context: Context?): ChatDatabase? {
            if (instance == null) {
                synchronized(ChatDatabase::class) {
                    instance = Room.databaseBuilder(
                        context!!,
                        ChatDatabase::class.java,
                        "chat_database"
                    ).build()
                }
            }
            return instance
        }
    }
}