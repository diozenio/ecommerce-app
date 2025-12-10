package com.example.ecommerceapp.data.saved

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ecommerceapp.data.core.BaseDao
import com.example.ecommerceapp.model.SavedItem

@Dao
interface SavedDao : BaseDao<SavedItem> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertOne(item: SavedItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertAll(items: List<SavedItem>)

    @Update
    override suspend fun updateOne(item: SavedItem)

    @Delete
    override suspend fun deleteOne(item: SavedItem)

    @Query("DELETE FROM saved")
    override suspend fun deleteAll()

    @Query("SELECT * FROM saved")
    override suspend fun findAll(): List<SavedItem>

    @Query("SELECT * FROM saved WHERE id = :id")
    suspend fun findById(id: Int): SavedItem?
}