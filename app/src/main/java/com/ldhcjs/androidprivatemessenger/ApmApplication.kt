package com.ldhcjs.androidprivatemessenger

import android.app.Application
import android.util.Log
import androidx.core.provider.FontRequest
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import androidx.room.Room
import com.ldhcjs.androidprivatemessenger.db.ChatDatabase

class ApmApplication : Application() {

    companion object {

        lateinit var apmApplication: ApmApplication
            private set

        lateinit var apmDBInstance: ChatDatabase
            private set

        private val TAG = "EmojiCompatApplication"

        /** Change this to `false` when you want to use the downloadable Emoji font.  */
        private val USE_BUNDLED_EMOJI = true

    }

    override fun onCreate() {
        super.onCreate()

        apmApplication = this

        apmDBInstance = Room.databaseBuilder(
            apmApplication.applicationContext,
            ChatDatabase::class.java,
            "chat_database"
        ).fallbackToDestructiveMigration() // DB 버전 다른경우 초기화
            .allowMainThreadQueries() // 메인스레드에서 접근 허용
            .build()

        val config: EmojiCompat.Config
        if (USE_BUNDLED_EMOJI) {
            // Use the bundled font for EmojiCompat
            config = BundledEmojiCompatConfig(applicationContext)
        } else {
            // Use a downloadable font for EmojiCompat
            val fontRequest = FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Noto Color Emoji Compat",
                R.array.com_google_android_gms_fonts_certs
            )
            config = FontRequestEmojiCompatConfig(applicationContext, fontRequest)
                .setReplaceAll(true)
                .registerInitCallback(object : EmojiCompat.InitCallback() {
                    override fun onInitialized() {
                        Log.i(TAG, "EmojiCompat initialized")
                    }

                    override fun onFailed(throwable: Throwable?) {
                        Log.e(TAG, "EmojiCompat initialization failed", throwable)
                    }
                })
        }
        EmojiCompat.init(config)
    }
}