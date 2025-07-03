package com.example.kotlin_chat_app.feature.chat

import androidx.compose.foundation.Image
import com.example.kotlin_chat_app.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kotlin_chat_app.models.Message
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.nio.file.WatchEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen_Chat(navControll: NavController,channelId:String){


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                title = { Text("Channel", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        navControll.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "", tint = Color.White)
                    }
                }

            )
        },
        containerColor = Color.Black
    ) {
        val viewModel: ChatModelView = hiltViewModel()
        LaunchedEffect(key1 = true) {
            viewModel.getListenMessages(channelId)
        }
        Column(
            Modifier.fillMaxSize().padding(it)
           ) {

            val message = viewModel.message.collectAsState()

            ChatMessages(
                message.value,
                onSendMessage = { message ->
                    viewModel.sendMessages(channelId, message)
                }
            )
        }
    }



}

@Composable
fun ChatMessages(
    messages: List<Message>,
    onSendMessage: (String) -> Unit

){
    val msg = remember{
        mutableStateOf("")
    }
    val keyboardcontroll = LocalSoftwareKeyboardController.current
    Box(
        modifier = Modifier.fillMaxSize()

    ){
        LazyColumn (
            modifier = Modifier.padding(bottom = 60.dp).fillMaxSize(),
           //     reverseLayout = true,
            verticalArrangement = Arrangement.Bottom
        ){
            items(messages) { message->
                ChatBuble(message)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(8.dp)
                .background(Color.LightGray),
            verticalAlignment = Alignment.CenterVertically


            ){
            TextField(value = msg.value, onValueChange = {msg.value=it},
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth() ,
                placeholder = {Text("type a message")},
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions (
                   onDone= {
                       keyboardcontroll?.hide()
                   }  )
            )
            IconButton(onClick = {
                onSendMessage(msg.value)
                msg.value= ""
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "")
            }
        }

    }

}
@Composable
fun ChatBuble(message: Message) {
    val isCurrentUser = message.senderId == Firebase.auth.currentUser?.uid
    val bubbleColor = if (isCurrentUser) {
        Color.Green
    } else {
        Color.Blue

    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clickable {}
    ) {
        val alignment = if (!isCurrentUser) Alignment.CenterStart else Alignment.CenterEnd
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)


                .align(alignment),
            verticalAlignment = Alignment.Bottom
        ) {

            if(!isCurrentUser){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        if(message.senderName.isNotEmpty()) message.senderName else "Friend",
                        style = TextStyle(color = Color.LightGray, fontSize = 10.sp),
                        modifier = Modifier.padding(bottom = 2.dp)
                    )

                    Image(
                        painter = painterResource(R.drawable.friend),
                        "",
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(6.dp))
            }

            Column(
                horizontalAlignment = (if (isCurrentUser) Alignment.End else Alignment.Start)

            ) {

                Text(
                    text = message.message,
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.White,
                    modifier = Modifier
                        .background(bubbleColor, shape = RoundedCornerShape(16.dp))
                        .padding(10.dp)
                        .widthIn(max = 250.dp, min = 20.dp)
                )

            }

        }

    }
}

fun formatTime(timeMillis: Long): String {
    val sdf = SimpleDateFormat("HH:mm dd/MM", Locale.getDefault())
    val date = Date(timeMillis)
    return sdf.format(date)
}

