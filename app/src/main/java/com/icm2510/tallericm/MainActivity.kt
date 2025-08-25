package com.icm2510.tallericm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.icm2510.tallericm.ui.navigation.NavigationStack
import com.icm2510.tallericm.ui.theme.TallerICMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TallerICMTheme {
                NavigationStack()
            }
        }
    }
}
