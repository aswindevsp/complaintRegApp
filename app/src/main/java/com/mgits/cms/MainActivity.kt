package com.mgits.cms


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mgits.cms.navigation.AppNavHost
import com.mgits.cms.ui.theme.cmsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            cmsAppTheme {
                   AppNavHost()
            }
        }
    }
}
