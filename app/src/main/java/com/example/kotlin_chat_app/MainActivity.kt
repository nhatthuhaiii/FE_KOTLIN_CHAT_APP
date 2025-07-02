package com.example.kotlin_chat_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kotlin_chat_app.feature.auth.login.Screen_Login
import com.example.kotlin_chat_app.feature.auth.register.Screen_Register
import com.example.kotlin_chat_app.feature.chat.Screen_Chat
import com.example.kotlin_chat_app.feature.home.Screen_Home
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        val currenUser = FirebaseAuth.getInstance().currentUser
        val star = if(currenUser != null) "home" else "login"
        NavHost(navController = navControll, startDestination = star) {

            composable("login") {
                Screen_Login(navControll)
            }
            composable("register") {
                Screen_Register(navControll)
            }
            composable("home"){
                Screen_Home(navControll)
            }
            composable (route="chat/{channelid}", arguments = listOf(
                navArgument("channelid"){
                    type= NavType.StringType
                }
            )){
                val channelid= it.arguments?.getString("channelId")?:""

                Screen_Chat(navControll,channelid)
            }

        }}
}