package com.example.notifaction

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import kotlin.random.Random

class NotificationHandler(private val context: Context) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    private val notificationChannelID = "notification_channel_id"

    fun showSimpleNotification(coinName:String,priceChangePercent:String) {
        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setContentTitle("Favorite Coin price")
            .setContentText(
                if (priceChangePercent.toDouble() > 0){
                    "the price of ${coinName} high by ${priceChangePercent.take(3)} percent"
                }else{
                    "the price of ${coinName} fell by ${priceChangePercent.replace("-", "").take(3)} percent"
                }
            )
            .setSmallIcon(com.example.multimodulecrypto.core.common.R.drawable.baseline_notifications_24)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}