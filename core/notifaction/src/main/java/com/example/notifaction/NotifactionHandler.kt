package com.example.notifaction

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import kotlin.random.Random

class NotificationHandler(private val context: Context) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    private val notificationChannelID = "notification_channel_id"

    fun showSimpleNotification(coinName:String,id:String,priceChangePercent:String) {

        val deepLinkUri = Uri.parse("myapp://coin/detail/$id")

        val intent = Intent(Intent.ACTION_VIEW, deepLinkUri)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

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
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}