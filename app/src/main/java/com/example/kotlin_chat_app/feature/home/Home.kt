package com.example.kotlin_chat_app.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun Screen_Home (navControl: NavHostController?){

    Scaffold (){innerPadding ->

        Column (
            modifier = Modifier.padding(innerPadding).fillMaxSize().background(Color.Red)
        ){

        }
    }


}
@Preview(showSystemUi = true)
@Composable
fun showUIHome(){
    Screen_Home(navControl = null)
}