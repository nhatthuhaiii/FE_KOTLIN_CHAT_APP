package com.example.kotlin_chat_app.feature.home

import androidx.lifecycle.ViewModel
import com.example.kotlin_chat_app.models.Channel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
@HiltViewModel
class HomeviewModel  @Inject constructor():  ViewModel(){
    val  firebaseDatabase = Firebase.database
    var _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channels = _channels.asStateFlow()
    init {
        getChannels()
    }

    private fun getChannels(){
        firebaseDatabase.getReference("channel").get().addOnSuccessListener {
            val list = mutableListOf<Channel>()
            it.children.forEach {
                data-> val channel = Channel(data.key!!,data.value.toString())
                list.add(channel)
            }
            _channels.value=list
        }
    }
    fun addChannel(name:String ){
        val key = firebaseDatabase.getReference("channel").push().key
        firebaseDatabase.getReference("channel").child(key!!).setValue(name).addOnSuccessListener {
            getChannels()
        }

    }

}