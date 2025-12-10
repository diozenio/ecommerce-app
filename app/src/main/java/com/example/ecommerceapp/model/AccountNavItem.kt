package com.example.ecommerceapp.model

import com.example.ecommerceapp.ui.components.UIIconName

sealed class AccountNavItem(
    val label: String,
    val icon: UIIconName,
    val route: String,
) {
    object MyOrdersItem : AccountNavItem(
        label = "My Orders",
        icon = UIIconName.Box,
        route = "my_orders"	
    )

    object MyDetailsItem : AccountNavItem(
        label = "My Details",
        icon = UIIconName.Details,
        route = ""
    )

    object AddressBookItem : AccountNavItem(
        label = "Address Book",
        icon = UIIconName.Address,
        route = ""
    )

    object PaymentMethodsItem : AccountNavItem(
        label = "Payment Methods",
        icon = UIIconName.Card,
        route = ""
    )

    object NotificationsItem : AccountNavItem(
        label = "Notifications",
        icon = UIIconName.Bell,
        route = ""
    )

    object FAQsItem : AccountNavItem(
        label = "FAQs",
        icon = UIIconName.Question,
        route = ""
    )

    object HelpCenterItem : AccountNavItem(
        label = "Help Center",
        icon = UIIconName.Headphones,
        route = ""
    )
}
