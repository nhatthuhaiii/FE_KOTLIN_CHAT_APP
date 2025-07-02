package com.example.kotlin_chat_app.feature.chat

import androidx.lifecycle.ViewModel
import com.example.kotlin_chat_app.models.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatModelView @Inject constructor (): ViewModel(){
    private val _message = MutableStateFlow<List<Message>>(emptyList())
    val message = _message.asStateFlow()
    val db = Firebase.database

    fun sendMessages(ChannelId:String,messageText: String){
       var message = Message(
           id = db.reference.push().key ?: UUID.randomUUID().toString(),
           senderId =  Firebase.auth.currentUser?.uid?:"",
           messageText,
           createAt = System.currentTimeMillis(),
           senderName = Firebase.auth.currentUser?.displayName?:"",
           senderImage = null,
           imageUrl = null
       )
        db.getReference("messages").child(ChannelId).push().setValue(message)
    }

    fun getListenMessages(channelId:String){
        db.getReference("messages")
            .child(channelId)
            .orderByChild("createAt")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list =mutableListOf<Message>()
                    snapshot.children.forEach { data ->
                        val message = data.getValue(Message::class.java)
                        message?.let {
                            list.add(it)
                        }
                    }
                    _message.value=list
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


    }

}


