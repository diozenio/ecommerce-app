package com.example.ecommerceapp.data.core

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ecommerceapp.data.notification.NotificationDao
import com.example.ecommerceapp.model.Notification
import com.example.ecommerceapp.model.NotificationCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppDatabaseCallback(
    private val scope: CoroutineScope,
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        scope.launch {
            val notificationDao = DatabaseHelper.INSTANCE?.notificationDao()

            notificationDao?.let { popularBancoDeDados(it) }
        }
    }

    private suspend fun popularBancoDeDados(notificationDao: NotificationDao) {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val listOfNotification = listOf(
            Notification(
                title = "Atualize seu Perfil",
                subtitle = "Complete seus dados para receber um cupom especial!",
                category = NotificationCategory.PROFILE,
                date = Date()
            ),
            Notification(
                title = "Pagamento Recebido",
                subtitle = "Seu pagamento do pedido #1234 foi processado com sucesso.",
                category = NotificationCategory.PAYMENT,
                date = Date()
            ),
            Notification(
                title = "Oferta Imperdível! 50% OFF",
                subtitle = "Corra e confira as novas promoções de inverno.",
                category = NotificationCategory.OFFER,
                date = formatter.parse("10/11/2025")!!
            ),
            Notification(
                title = "Pagamento Recebido",
                subtitle = "Seu pagamento do pedido #1234 foi processado com sucesso.",
                category = NotificationCategory.PAYMENT,
                date = formatter.parse("25/10/2025")!!
            ), Notification(
                title = "Atualize seu Perfil",
                subtitle = "Complete seus dados para receber um cupom especial!",
                category = NotificationCategory.PROFILE,
                date = Date()
            ),
            Notification(
                title = "Pagamento Recebido",
                subtitle = "Seu pagamento do pedido #1234 foi processado com sucesso.",
                category = NotificationCategory.PAYMENT,
                date = Date()
            ),
            Notification(
                title = "Oferta Imperdível! 50% OFF",
                subtitle = "Corra e confira as novas promoções de inverno.",
                category = NotificationCategory.OFFER,
                date = formatter.parse("10/11/2025")!!
            ),
            Notification(
                title = "Pagamento Recebido",
                subtitle = "Seu pagamento do pedido #1234 foi processado com sucesso.",
                category = NotificationCategory.PAYMENT,
                date = formatter.parse("25/10/2025")!!
            ), Notification(
                title = "Atualize seu Perfil",
                subtitle = "Complete seus dados para receber um cupom especial!",
                category = NotificationCategory.PROFILE,
                date = Date()
            ),
            Notification(
                title = "Pagamento Recebido",
                subtitle = "Seu pagamento do pedido #1234 foi processado com sucesso.",
                category = NotificationCategory.PAYMENT,
                date = Date()
            ),
            Notification(
                title = "Oferta Imperdível! 50% OFF",
                subtitle = "Corra e confira as novas promoções de inverno.",
                category = NotificationCategory.OFFER,
                date = formatter.parse("10/11/2025")!!
            ),
            Notification(
                title = "Pagamento Recebido",
                subtitle = "Seu pagamento do pedido #1234 foi processado com sucesso.",
                category = NotificationCategory.PAYMENT,
                date = formatter.parse("25/10/2025")!!
            )
        )

        notificationDao.insertAll(listOfNotification)
    }
}