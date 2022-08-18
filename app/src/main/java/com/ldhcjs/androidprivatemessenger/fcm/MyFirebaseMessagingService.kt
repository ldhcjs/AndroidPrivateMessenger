package com.ldhcjs.androidprivatemessenger.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ldhcjs.androidprivatemessenger.MainActivity
import com.ldhcjs.androidprivatemessenger.R
import com.ldhcjs.androidprivatemessenger.db.ChatDatabase
import com.ldhcjs.androidprivatemessenger.db.entity.ChatEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val tag = MyFirebaseMessagingService::class.java.simpleName

    override fun onNewToken(token: String) {
        Log.d(tag, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
    }

    override fun onMessageReceived(rm: RemoteMessage) {
        Log.d(tag, "Received msg: " + rm.data)
        sendNotification(rm.data["time"].toString())
        super.onMessageReceived(rm)

        val chatData = ChatEntity(
            rm.data["title"].toString(),
            rm.data["msg"].toString(),
            rm.data["name"].toString(),
            rm.data["time"].toString(),
            //rm.data["who"].toString(),
            "other",
            rm.data["group"].toString()
        )
        val db = ChatDatabase.getInstance(applicationContext)
        // 비동기 동작 코루틴 동작
        CoroutineScope(Dispatchers.IO).launch {
            db!!.chatDao().insert(chatData)
//            Log.d(tag, "fcm chatlist " + db.chatDao().selectAllChat())
        }
    }

    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelName = getString(R.string.channel_name)
        val channelId = getString(R.string.channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_chat_noti)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Msg Push"
            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(this)) {
            notify(Integer.parseInt(channelId), notificationBuilder.build())
        }
//        notificationManager.notify(Integer.parseInt(channelId), notificationBuilder.build())
    }
}