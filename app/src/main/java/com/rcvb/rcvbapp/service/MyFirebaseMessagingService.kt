package com.rcvb.rcvbapp.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.Navigation
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.adapter.ArticleAdapter
import com.rcvb.rcvbapp.onboarding.fragments.ArticleFragment
import java.util.*

class MyFirebaseMessagingService: FirebaseMessagingService() {

    private val TAG = "MyFirebaseMessagingService"

    companion object {
        const val CHANNEL_ID = "CHANNEL"
    }

    private lateinit var manager: NotificationManager

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Action
        val intent = Intent(this, ArticleFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        // Créer une notification

        val message = remoteMessage.notification?.body
        Log.d(TAG, "Vous venez de recevoir une notification : $message ")

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_liked)
            .setContentTitle("Nouvel article")
            .setContentText(message)

        // Ajouter l'action
        builder.setContentIntent(pendingIntent)

        // Créer la vibration
        val vibrationPattern: LongArray = longArrayOf(500, 1000)
        builder.setVibrate(vibrationPattern)

        // Envoyer la notification

        manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.notification_channel_id)
            val channelTitle = getString(R.string.notification_channel_title)
            val channelDesc = getString(R.string.notification_channel_desc)

            val channel = NotificationChannel(channelId, channelTitle, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = channelDesc
            manager.createNotificationChannel(channel)
            builder.setChannelId(channelId)
        }

        manager.notify(0, builder.build())

    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d(TAG, "New Token")
    }

}