package com.example.kotlin_chat_app.feature.auth.login

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor() : ViewModel(){
 private val  _state = MutableStateFlow<LoginState>(LoginState.Nothing)
        val state = _state.asStateFlow()
    fun logIn(email:String,password:String) {
        _state.value = LoginState.Loading

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                task->
                if(task.isSuccessful) {
                    task.result.user?.let {
                        _state.value = LoginState.Success
                        return@addOnCompleteListener
                    }
                    _state.value = LoginState.Error
                }

                else
                    _state.value = LoginState.Error
            }







    }




}

sealed class LoginState{
    object Nothing: LoginState()
    object Loading: LoginState()
    object Success: LoginState()
    object Error : LoginState()

}
