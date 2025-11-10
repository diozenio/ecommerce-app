package com.example.ecommerceapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun UIInput(
    modifier: Modifier = Modifier,
    text: String,
    onChangeValue: (String) -> Unit,
    leadingIcon: UIIconName? = null,
    trailingIcon: UIIconName? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    trailingIconColor: Color = Colors.Primary300,
    placeholderText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    supportingText: String? = null
) {

    OutlinedTextField(
        value = text,
        onValueChange = onChangeValue,
        leadingIcon = if (leadingIcon != null) {
            {
                UIIcon(
                    icon = leadingIcon,
                    color = Colors.Primary300,
                )
            }
        } else {
            null
        },
        trailingIcon = if (trailingIcon != null) {
            {
                if (onTrailingIconClick != null) {
                    IconButton(onClick = onTrailingIconClick) {
                        UIIcon(
                            icon = trailingIcon,
                            color = trailingIconColor
                        )
                    }
                } else {
                    UIIcon(
                        icon = trailingIcon,
                        color = trailingIconColor
                    )
                }
            }
        } else {
            null
        },
        placeholder = {
            if (placeholderText != null) {
                Text(
                    text = placeholderText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Colors.Primary400,
                )
            }
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        enabled = enabled,
        readOnly = readOnly,
        isError = isError,
        supportingText = if (supportingText != null) {
            { Text(supportingText) }
        } else {
            null
        },
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Colors.Primary100,
            focusedBorderColor = Colors.Primary900,
            focusedTextColor = Colors.Primary900,
        ),
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun UIInputPreview(){
    Column(modifier = Modifier.padding(16.dp)) {
        UIInput(
            text = "",
            onChangeValue = {},
            placeholderText = "Digite seu e-mail"
        )

        Spacer(modifier = Modifier.height(8.dp))

        UIInput(
            text = "texto",
            onChangeValue = {},
            placeholderText = "Campo com erro",
            isError = true,
            supportingText = "Esta é uma mensagem de erro."
        )
    }
}