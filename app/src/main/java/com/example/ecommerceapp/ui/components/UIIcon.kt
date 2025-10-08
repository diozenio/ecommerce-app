package com.example.ecommerceapp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.R

enum class UIIconName {
    Address,
    ApplePayLogo,
    Arrow,
    Bag,
    Bell,
    BellDuotone,
    Box,
    BoxDuotone,
    Calendar,
    Cancel,
    CancelCircle,
    Card,
    CardDuotone,
    Cart,
    CartDuotone,
    Cash,
    Chat,
    Check,
    CheckCircle,
    CheckDuotone,
    Chevron,
    Circle,
    Details,
    Discount,
    DiscountDuotone,
    Edit,
    Eye,
    EyeOff,
    Facebook,
    FacebookLogo,
    Filter,
    GoogleLogo,
    Headphones,
    Heart,
    HeartDuotone,
    HeartFilled,
    Home,
    Image,
    Instagram,
    Location,
    LocationDuotone,
    LocationFilled,
    Logout,
    Mastercard,
    Mic,
    Minus,
    Phone,
    PhoneFilled,
    Plus,
    Question,
    Return,
    Search,
    SearchDuotone,
    Settings,
    Star,
    Trash,
    TruckFilled,
    Twitter,
    UnitedStates,
    User,
    Users,
    UserDuotone,
    Visa,
    WalletDuotone,
    WarehouseFilled,
    Warning,
    WarningCircle,
    Web,
    Whatsapp
}

@Composable
fun UIIcon(
    icon: UIIconName,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = Color.Unspecified
) {
    val resId = when (icon) {
        UIIconName.Address -> R.drawable.address
        UIIconName.ApplePayLogo -> R.drawable.apple_pay_logo
        UIIconName.Arrow -> R.drawable.arrow
        UIIconName.Bag -> R.drawable.bag
        UIIconName.Bell -> R.drawable.bell
        UIIconName.BellDuotone -> R.drawable.bell_duotone
        UIIconName.Box -> R.drawable.box
        UIIconName.BoxDuotone -> R.drawable.box_duotone
        UIIconName.Calendar -> R.drawable.calendar
        UIIconName.Cancel -> R.drawable.cancel
        UIIconName.CancelCircle -> R.drawable.cancel_circle
        UIIconName.Card -> R.drawable.card
        UIIconName.CardDuotone -> R.drawable.card_duotone
        UIIconName.Cart -> R.drawable.cart
        UIIconName.CartDuotone -> R.drawable.cart_duotone
        UIIconName.Cash -> R.drawable.cash
        UIIconName.Chat -> R.drawable.chat
        UIIconName.Check -> R.drawable.check
        UIIconName.CheckCircle -> R.drawable.check_circle
        UIIconName.CheckDuotone -> R.drawable.check_duotone
        UIIconName.Chevron -> R.drawable.chevron
        UIIconName.Circle -> R.drawable.circle
        UIIconName.Details -> R.drawable.details
        UIIconName.Discount -> R.drawable.discount
        UIIconName.DiscountDuotone -> R.drawable.discount_duotone
        UIIconName.Edit -> R.drawable.edit
        UIIconName.Eye -> R.drawable.eye
        UIIconName.EyeOff -> R.drawable.eye_off
        UIIconName.Facebook -> R.drawable.facebook
        UIIconName.FacebookLogo -> R.drawable.facebook_logo
        UIIconName.Filter -> R.drawable.filter
        UIIconName.GoogleLogo -> R.drawable.google_logo
        UIIconName.Headphones -> R.drawable.headphones
        UIIconName.Heart -> R.drawable.heart
        UIIconName.HeartDuotone -> R.drawable.heart_duotone
        UIIconName.HeartFilled -> R.drawable.heart_filled
        UIIconName.Home -> R.drawable.home
        UIIconName.Image -> R.drawable.image
        UIIconName.Instagram -> R.drawable.instagram
        UIIconName.Location -> R.drawable.location
        UIIconName.LocationDuotone -> R.drawable.location_duotone
        UIIconName.LocationFilled -> R.drawable.location_filled
        UIIconName.Logout -> R.drawable.logout
        UIIconName.Mastercard -> R.drawable.mastercard
        UIIconName.Mic -> R.drawable.mic
        UIIconName.Minus -> R.drawable.minus
        UIIconName.Phone -> R.drawable.phone
        UIIconName.PhoneFilled -> R.drawable.phone_filled
        UIIconName.Plus -> R.drawable.plus
        UIIconName.Question -> R.drawable.question
        UIIconName.Return -> R.drawable.resource_return
        UIIconName.Search -> R.drawable.search
        UIIconName.SearchDuotone -> R.drawable.search_duotone
        UIIconName.Settings -> R.drawable.settings
        UIIconName.Star -> R.drawable.star
        UIIconName.Trash -> R.drawable.trash
        UIIconName.TruckFilled -> R.drawable.truck_filled
        UIIconName.Twitter -> R.drawable.twitter
        UIIconName.UnitedStates -> R.drawable.united_states
        UIIconName.User -> R.drawable.user
        UIIconName.Users -> R.drawable.users
        UIIconName.UserDuotone -> R.drawable.user_duotone
        UIIconName.Visa -> R.drawable.visa
        UIIconName.WalletDuotone -> R.drawable.wallet_duotone
        UIIconName.WarehouseFilled -> R.drawable.warehouse_filled
        UIIconName.Warning -> R.drawable.warning
        UIIconName.WarningCircle -> R.drawable.warning_circle
        UIIconName.Web -> R.drawable.web
        UIIconName.Whatsapp -> R.drawable.whatsapp
    }

    Icon(
        painter = painterResource(id = resId),
        contentDescription = icon.name,
        tint = color,
        modifier = modifier.size(size)
    )
}
