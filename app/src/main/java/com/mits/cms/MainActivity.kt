package com.mits.cms



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.mits.cms.navigation.AppNavHost
import com.mits.cms.ui.theme.cmsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            cmsAppTheme {
                Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
                    AppNavHost()
                }
            }
        }
    }
}
