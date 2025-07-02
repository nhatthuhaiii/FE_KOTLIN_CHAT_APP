package com.example.kotlin_chat_app.feature.auth.register

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class RegisterViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow<RegisterState>(RegisterState.Nothing)
    val state =_state.asStateFlow()

    fun register(name:String, email:String,password:String ){
        _state.value = RegisterState.Nothing
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    task.result.user?.let {
                        it.updateProfile(
                            com.google.firebase.auth.UserProfileChangeRequest
                                .Builder().setDisplayName(name).build()

                        )?.addOnCompleteListener {
                            _state.value= RegisterState.Success
                        }
                        return@addOnCompleteListener
                    }
                    _state.value= RegisterState.Error
                }else {
                    _state.value= RegisterState.Error
                }

            }


    }





}
sealed class RegisterState{
    object Nothing: RegisterState()
    object Loading: RegisterState()
    object Success: RegisterState()
    object Error : RegisterState()

}