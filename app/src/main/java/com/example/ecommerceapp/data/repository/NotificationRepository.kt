package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.data.notification.LocalNotificationDataSource
import com.example.ecommerceapp.data.notification.RemoteNotificationApiDataSource
import com.example.ecommerceapp.model.Notification

class NotificationRepository(
    private val localDataSource: LocalNotificationDataSource,
    private val remoteDataSource: RemoteNotificationApiDataSource
) {

    suspend fun findAll(): List<Notification> {
        return try {
            val remoteNotifications = remoteDataSource.findAll()

            localDataSource.upsertAll(remoteNotifications)

            remoteNotifications
        } catch (e: Exception) {
            localDataSource.findAll()
        }
    }
}