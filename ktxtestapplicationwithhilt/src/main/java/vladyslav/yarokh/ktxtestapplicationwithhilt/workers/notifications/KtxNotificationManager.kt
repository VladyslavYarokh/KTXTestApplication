package vladyslav.yarokh.ktxtestapplicationwithhilt.workers.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import vladyslav.yarokh.ktxtestapplicationwithhilt.MainActivity
import vladyslav.yarokh.ktxtestapplicationwithhilt.R

internal class KtxNotificationManager (private val context: Context) {
    fun createNotificationChannel(channelID: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = context.getString(R.string.channel_name)

            val channel = NotificationChannel(
                channelID, channelName, NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = context.getString(R.string.channel_description)
                enableLights(true)
                enableVibration(true)
                lightColor = Color.GREEN
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }

            val notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification(channelID: String, title: String?, message: String?) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        createNotification(channelID, title, message, intent)
    }

    private fun createNotification(
        channelID: String,
        title: String?,
        message: String?,
        intent: Intent
    ) {
        val notificationTitle = title ?: context.resources.getString(R.string.app_name)
        val notificationMessage = message ?: ""

        val pendingIntent = PendingIntent.getActivity(
            context, NOTIFICATION_CHILD_ID,
            intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val childNotification =
            NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(notificationTitle)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentText(notificationMessage)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(notificationMessage)
                )
                .setGroup(GROUP_KEY_NOTIFY)

        val summaryNotification = NotificationCompat.Builder(context, channelID)
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setSmallIcon(R.drawable.ic_logo)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.InboxStyle().addLine(notificationMessage))
            .setGroup(GROUP_KEY_NOTIFY)
            .setGroupSummary(true)

        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(
            NOTIFICATION_CHILD_ID++,
            childNotification.build()
        )
        notificationManager.notify(
            NOTIFICATION_ID,
            summaryNotification.build()
        )
    }

    companion object {
        const val GROUP_KEY_NOTIFY = "beeline.android.NOTIFY"
        const val NOTIFICATION_ID = 0
        var NOTIFICATION_CHILD_ID = 1
    }
}