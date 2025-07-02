package com.example.kotlin_chat_app.models

data class Channel (
    val id: String = "",
    val name:String="",
    val createAt: Long = System.currentTimeMillis()
)