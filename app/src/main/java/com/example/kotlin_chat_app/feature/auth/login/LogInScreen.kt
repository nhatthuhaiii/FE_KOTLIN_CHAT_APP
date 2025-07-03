package com.example.kotlin_chat_app.feature.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_chat_app.R
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Screen_Login(navController: NavController){
    val viewModel: LogInViewModel  = hiltViewModel()
    val  UIState  = viewModel.state.collectAsState()
    var email  by remember {
        mutableStateOf("nhat@gmai.com")
    }
    var pass by remember {
        mutableStateOf("123456")
    }

    var context = LocalContext.current

    LaunchedEffect(UIState.value) {
            when (UIState.value){
                is LoginState.Success ->{
                    navController.navigate("home")
                }
                is LoginState.Error ->{
                    Toast.makeText(context,"Sign in failed",Toast.LENGTH_SHORT).show()

                }
                else ->{}
            }



    }


    Scaffold (modifier = Modifier.fillMaxSize()) {
        Column (
            modifier = Modifier.fillMaxSize().padding(it).padding(all = 16.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier= Modifier.size(160.dp).background(Color.White)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email, onValueChange = {email = it},
                placeholder ={Text("Email")},
                label = {Text("Email")})
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = pass, onValueChange = {pass= it},
                label = {Text("Password")},
                placeholder = {Text("Password")},
                visualTransformation = PasswordVisualTransformation())
            Spacer(modifier = Modifier.size(16.dp))
            if(UIState.value == LoginState.Loading){
                CircularProgressIndicator()
            }
            else {
            Button(onClick = {viewModel.logIn(email,pass)},
                modifier = Modifier.fillMaxWidth(),
                enabled = email.isNotEmpty()&& pass.isNotEmpty() && (UIState.value== LoginState.Error || UIState.value == LoginState.Nothing))
                 {
                Text("Sign In")

                 }
            TextButton(onClick = { navController.navigate("register") }) {
                Text("Don't have an account? Register")
            }


             }
        }
    }


}




@Preview(showSystemUi = true)
@Composable
fun ShowLogInUI(){
    Screen_Login(navController = rememberNavController())
}