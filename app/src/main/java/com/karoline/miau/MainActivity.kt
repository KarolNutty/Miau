package com.karoline.miau

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.karoline.miau.screen.ChatScreen
import com.karoline.miau.screen.SplashScreen
import com.karoline.miau.ui.theme.MiauTheme
import com.karoline.miau.viewmodel.ChatViewModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MiauTheme {
                val navController = rememberNavController()

                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") {
                            SplashScreen(onTimeout = {
                                navController.navigate("chat") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            })
                        }
                        composable("chat") {
                            ChatScreen(viewModel = ChatViewModel())
                        }
                    }
                }
            }
        }
    }
}



