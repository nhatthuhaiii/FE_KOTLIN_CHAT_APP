package com.example.kotlin_chat_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_chat_app.feature.auth.login.Screen_Login
import com.example.kotlin_chat_app.feature.auth.register.Screen_Register

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainApp()
        }
    }
}


@Composable
fun MainApp(){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        var navControll = rememberNavController()

        NavHost(navController = navControll, startDestination = "login"){

            composable( "login") {
                Screen_Login(navControll)
            }
            composable ("register"){
                Screen_Register(navControll)
            }
        }

    }
}