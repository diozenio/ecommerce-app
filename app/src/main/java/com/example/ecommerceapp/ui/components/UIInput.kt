package com.example.ecommerceapp.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun UIInput(
    text: String,
    onChangeValue: (String) -> Unit,
    leadingIcon: UIIconName? = null,
    trailingIcon: UIIconName? = null,
    placeholderText: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    modifier: Modifier
) {

    OutlinedTextField(
        value = text,
        onValueChange = onChangeValue,
        leadingIcon = {
            if (leadingIcon != null) {
                UIIcon(
                    icon = leadingIcon,
                    color = Colors.Primary300,

                )
            }
        },
        trailingIcon = {
            if (trailingIcon != null) {
                UIIcon(
                    icon = trailingIcon,
                    color = Colors.Primary300,
                )
            }
        },
        placeholder = {
            if (placeholderText != null) {
                UIText(
                    text = placeholderText,
                    variant = UITextVariant.B1,
                    weight = UITextWeight.Regular,
                    color = Colors.Primary400,
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = singleLine,
        enabled = enabled,
        readOnly = readOnly,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Colors.Primary100,
            focusedBorderColor = Colors.Primary900,
            focusedTextColor = Colors.Primary900,
        ),
        modifier = modifier,
    )
}