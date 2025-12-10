package com.example.ecommerceapp.data.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ecommerceapp.data.auth.UserSessionDao
import com.example.ecommerceapp.data.cart.CartDao
import com.example.ecommerceapp.data.notification.NotificationConverter
import com.example.ecommerceapp.data.notification.NotificationDao
import com.example.ecommerceapp.data.order.OrderDao
import com.example.ecommerceapp.data.product.ProductConverter
import com.example.ecommerceapp.data.review.ReviewDao
import com.example.ecommerceapp.data.product.ProductDao
import com.example.ecommerceapp.data.product.category.CategoryDao
import com.example.ecommerceapp.model.CartItem
import com.example.ecommerceapp.model.Category
import com.example.ecommerceapp.model.Notification
import com.example.ecommerceapp.model.Order
import com.example.ecommerceapp.model.OrderReview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.UserSession


@Database(
    version = 5,
    entities = [CartItem::class, Notification::class, UserSession::class, OrderReview::class, Product::class, Category::class, Order::class],
    exportSchema = false
)
@TypeConverters(ProductConverter::class, NotificationConverter::class)

abstract class DatabaseHelper : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userSessionDao(): UserSessionDao
    abstract fun notificationDao(): NotificationDao
    abstract fun reviewDao(): ReviewDao
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        var INSTANCE: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            return INSTANCE ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context,
                    DatabaseHelper::class.java,
                    "ecommerce.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
