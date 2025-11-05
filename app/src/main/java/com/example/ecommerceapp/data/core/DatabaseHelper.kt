package com.example.ecommerceapp.data.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ecommerceapp.data.cart.CartDao
import com.example.ecommerceapp.data.product.ProductConverter
import com.example.ecommerceapp.model.CartItem

@Database(
    version = 1,
    entities = [CartItem::class]
)
@TypeConverters(ProductConverter::class)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    DatabaseHelper::class.java,
                    "ecommerce.db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
