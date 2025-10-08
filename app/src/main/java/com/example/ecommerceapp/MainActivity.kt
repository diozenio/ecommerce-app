package com.example.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.ui.components.UIButton
import com.example.ecommerceapp.ui.components.UIButtonVariant
import com.example.ecommerceapp.ui.components.UIIcon
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.components.UIText
import com.example.ecommerceapp.ui.components.UITextVariant
import com.example.ecommerceapp.ui.components.UITextWeight
import com.example.ecommerceapp.ui.theme.Colors
import com.example.ecommerceapp.ui.theme.EcommerceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcommerceAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            UIIcon(UIIconName.Home, color = Colors.Primary900)
                            Greeting(name = "My Cart")
                        }
                        UIButton(
                            text = "Primary",
                            variant = UIButtonVariant.Primary,
                            leftIcon = {
                                UIIcon(
                                    icon = UIIconName.Plus,
                                    color = Colors.Primary0,
                                    size = 16.dp
                                )
                            }
                        )
                        UIButton(
                            text = "Secondary",
                            variant = UIButtonVariant.Secondary,
                            rightIcon = {
                                UIIcon(
                                    icon = UIIconName.HeartFilled,
                                    size = 16.dp,
                                )
                            })
                        UIButton(text = "Disabled", variant = UIButtonVariant.Disabled)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    UIText(
        text = name,
        modifier = modifier,
        weight = UITextWeight.SemiBold,
        variant = UITextVariant.H3
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EcommerceAppTheme {
        Greeting("Android")
    }
}