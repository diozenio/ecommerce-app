package com.example.ecommerceapp.data.auth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerceapp.model.UserSession
import kotlinx.coroutines.flow.Flow

@Dao
interface UserSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSession(session: UserSession)

    @Query("DELETE FROM user_session_table")
    suspend fun deleteSession()

    @Query("SELECT * FROM user_session_table LIMIT 1")
    fun getActiveSession(): Flow<UserSession?>
}