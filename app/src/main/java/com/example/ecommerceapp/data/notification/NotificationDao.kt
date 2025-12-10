package com.example.ecommerceapp.data.notification

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.ecommerceapp.data.core.BaseLocalDataSource
import com.example.ecommerceapp.model.Notification

@Dao
interface NotificationDao : BaseLocalDataSource<Notification> {

    @Insert
    override suspend fun insertOne(item: Notification)

    @Insert
    suspend fun insertAll(items: List<Notification>)

    @Update
    override suspend fun updateOne(item: Notification)

    @Delete
    override suspend fun deleteOne(item: Notification)

    @Query("DELETE FROM notification")
    override suspend fun deleteAll()

    @Query("SELECT * FROM notification")
    override suspend fun findAll(): List<Notification>

    @Query("SELECT * FROM notification WHERE id = :id")
    override suspend fun findById(id: Int): Notification?

    @Upsert
    override suspend fun upsertAll(items: List<Notification>) {
        TODO("Not yet implemented")
    }
}