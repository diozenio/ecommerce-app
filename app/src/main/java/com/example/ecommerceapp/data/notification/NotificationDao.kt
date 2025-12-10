package com.example.ecommerceapp.data.notification

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ecommerceapp.data.core.BaseDao
import com.example.ecommerceapp.model.Notification

@Dao
interface NotificationDao : BaseDao<Notification> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertOne(item: Notification)

    @Update
    override suspend fun updateOne(item: Notification)

    @Delete
    override suspend fun deleteOne(item: Notification)

    @Query("DELETE FROM notification")
    override suspend fun deleteAll()

    @Query("SELECT * FROM notification")
    override suspend fun findAll(): List<Notification>

    @Query("SELECT * FROM notification WHERE id = :id")
    suspend fun findById(id: Int): Notification?
}