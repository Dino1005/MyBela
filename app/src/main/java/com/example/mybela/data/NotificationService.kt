package com.example.mybela.data

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.mybela.R

class NotificationService (
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(winner: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("$winner won the game!")
            .setContentText("See the result in previous games screen.")
            .setSmallIcon(R.drawable.logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
    }

    companion object{
        const val CHANNEL_ID = "channel_id"
    }
}