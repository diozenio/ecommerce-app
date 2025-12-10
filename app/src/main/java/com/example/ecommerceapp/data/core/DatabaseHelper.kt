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
import com.example.ecommerceapp.data.product.ProductConverter
import com.example.ecommerceapp.data.review.ReviewDao
import com.example.ecommerceapp.data.saved.SavedDao
import com.example.ecommerceapp.model.CartItem
import com.example.ecommerceapp.model.Notification
import com.example.ecommerceapp.model.OrderReview
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.SavedItem
import com.example.ecommerceapp.model.UserSession
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [
        UserSession::class,
        Product::class,
        CartItem::class,
        Notification::class,
        SavedItem::class,
        OrderReview::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(ProductConverter::class, NotificationConverter::class)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun userSessionDao(): UserSessionDao
    abstract fun cartDao(): CartDao
    abstract fun notificationDao(): NotificationDao
    abstract fun savedDao(): SavedDao
    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile
        internal var INSTANCE: DatabaseHelper? = null

        fun getInstance(context: Context, scope: CoroutineScope): DatabaseHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseHelper::class.java,
                    "ecommerce_db"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}