package com.yunusbedir.cryptocurrencypricetrackerapp.worker

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.LoginActivity
import com.yunusbedir.cryptocurrencypricetrackerapp.R
import com.yunusbedir.cryptocurrencypricetrackerapp.data.CoinRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.CoinDetail
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.CoinDetail.Companion.toCoinDetail
import com.yunusbedir.cryptocurrencypricetrackerapp.worker.NotificationConstants.CONTEXT_TEXT
import com.yunusbedir.cryptocurrencypricetrackerapp.worker.NotificationConstants.NOTIFICATION_DATA
import com.yunusbedir.cryptocurrencypricetrackerapp.worker.NotificationConstants.NOTIFICATION_EXTRA
import com.yunusbedir.cryptocurrencypricetrackerapp.worker.NotificationConstants.NOTIFICATION_ID
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.tasks.await

@HiltWorker
class FetchDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val coinRepository: CoinRepository
) : CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {
        try {
            coinRepository.getFavoriteCoinList().await().documents.forEach { document ->
                document.toCoinDetail()?.let {
                    val response = coinRepository.getCoinDetail(it.id)
                    if (
                        (it.marketData?.currentPrice ?: 0) != (response.marketData?.currentPrice
                            ?: 0)
                    ) {
                        coinRepository.setFavoriteCoin(response)
                        createNotification(response)
                    }
                }
            }
        } catch (e: Exception) {
            return Result.retry()
        }
        return Result.success()
    }

    private fun createNotification(response: CoinDetail) {
        val notifyIntent = Intent(applicationContext, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        notifyIntent.putExtra(NOTIFICATION_EXTRA, true)
        notifyIntent.putExtra(NOTIFICATION_DATA, response.id)
        val notifyPendingIntent = PendingIntent.getActivity(
            applicationContext, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat
            .Builder(applicationContext, applicationContext.packageName)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(CONTEXT_TEXT)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setChannelId(applicationContext.packageName)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(notifyPendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    companion object {
        const val WORK_NAME = "worker_notification"
    }
}