package com.example.kotlin_chat_app.models

    data class Message(
        val id: String? = "",
        val senderId: String ="",
        val channelId:String = "",
        val message: String ="",
        val createAt: Long = System.currentTimeMillis(),
        val senderName: String ="",
        val senderImage: String? =null,
        val imageUrl: String? =null
)
