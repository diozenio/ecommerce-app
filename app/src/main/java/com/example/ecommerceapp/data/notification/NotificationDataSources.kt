package com.example.ecommerceapp.data.notification

import com.example.ecommerceapp.model.Notification
import retrofit2.http.GET

interface LocalNotificationDataSource {
    suspend fun upsertAll(notifications: List<Notification>)
    suspend fun findAll(): List<Notification>
    suspend fun deleteAll()
}

class RoomLocalNotificationDataSource(
    private val dao: NotificationDao
) : LocalNotificationDataSource {

    override suspend fun upsertAll(notifications: List<Notification>) {
        dao.insertAll(notifications)
    }

    override suspend fun findAll(): List<Notification> {
        return dao.findAll()
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }
}

interface RemoteNotificationApiDataSource {
    @GET("notifications")
    suspend fun findAll(): List<Notification>
}