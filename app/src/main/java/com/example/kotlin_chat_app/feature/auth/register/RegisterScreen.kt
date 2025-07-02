package com.example.kotlin_chat_app.feature.auth.register

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_chat_app.R
import com.example.kotlin_chat_app.feature.auth.login.LoginState

@Composable
fun Screen_Register(navController: NavController ){
    var name  by remember {
        mutableStateOf("")
    }

    var email  by remember {
        mutableStateOf("")
    }
    var pass by remember {
        mutableStateOf("")
    }
    var repass by remember {
        mutableStateOf("")
    }
    val viewModel: RegisterViewModel = hiltViewModel()
    var context = LocalContext.current
    var UIState = viewModel.state.collectAsState()
    LaunchedEffect(UIState.value) {
        when (UIState.value){
            is RegisterState.Success ->{
                navController.navigate("home")
            }
            is RegisterState.Error ->{
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
                value = name, onValueChange = {name = it},
                placeholder ={Text("Full Name")},
                label = {Text("Full Name")})
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
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = repass, onValueChange = {repass= it},
                label = {Text("Confirm Password")},
                placeholder = {Text("Confirm Password")},
                visualTransformation = PasswordVisualTransformation(),
                isError =  pass.isEmpty() && repass.isEmpty() && pass != repass,)

            Spacer(modifier = Modifier.size(16.dp))
            if(UIState.value == RegisterState.Loading){
                CircularProgressIndicator()
            }
                Button(
                    onClick = {
                        viewModel.register(name, email, pass)

                    }, modifier = Modifier.fillMaxWidth(),
                    enabled = name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && repass.isNotEmpty() && repass == pass
                ) {
                    Text("Register")

                }
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Already have an account? Log In")
                }
            }

        }
    }



@Preview(showSystemUi = true)
@Composable
fun ShowUiRegister(){
    Screen_Register(navController = rememberNavController())
}


