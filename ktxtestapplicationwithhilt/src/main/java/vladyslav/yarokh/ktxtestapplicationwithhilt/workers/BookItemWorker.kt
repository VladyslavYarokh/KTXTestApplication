package vladyslav.yarokh.ktxtestapplicationwithhilt.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import vladyslav.yarokh.ktxtestapplicationwithhilt.data.BookModel
import vladyslav.yarokh.ktxtestapplicationwithhilt.workers.notifications.KtxNotificationManager

private const val NAME = "name"
private const val AUTHOR = "author"

internal class BookItemWorker(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {
    private val channelID by lazy {
        "main_channel"
    }

    private val notificationManager: KtxNotificationManager by lazy {
        KtxNotificationManager(context)
    }

    override fun doWork(): Result {
        return try {
            notificationManager.createNotificationChannel(channelID)
            notificationManager.sendNotification(channelID, inputData.keyValueMap[NAME].toString(), inputData.keyValueMap[AUTHOR].toString())
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }
}