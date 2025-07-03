package com.example.kotlin_chat_app.feature.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kotlin_chat_app.models.Channel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen_Home (navControl: NavHostController?){
    val viewModel = hiltViewModel<HomeviewModel>()
    val channels = viewModel.channels.collectAsState()
    val addchannel = remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    Scaffold (
            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Blue)
                        .clickable {
                            addchannel.value = true
                        }
                ){
                    Text("Add channel",
                    modifier = Modifier.padding(16.dp), color = Color.White
                        )


                }

            }


    ){innerPadding ->
        Box (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Black)
        ){
            LazyColumn (){
                item {
                    Row (
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            "Chat Nowww", modifier = Modifier.padding(16.dp), color = Color.White,
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Black)
                        )
                        IconButton(onClick = {
                            Firebase.auth.signOut()
                            navControl?.navigate("login"){
                                popUpTo (0)
                            }



                        }) {
                            Icon(Icons.Default.ArrowForward, contentDescription = "", tint = Color.White)

                        }
                    }
                }

                item {
                    TextField(value = "",
                        onValueChange = {},
                        placeholder = { Text(text = "Search...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clip(
                                RoundedCornerShape(40.dp)
                            ),
                        textStyle = TextStyle(color = Color.LightGray),
                        colors = TextFieldDefaults.colors().copy(
                            focusedContainerColor =Color.DarkGray,
                            unfocusedContainerColor = Color.DarkGray,
                            focusedTextColor = Color.Gray,
                            unfocusedTextColor = Color.Gray,
                            focusedPlaceholderColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.Gray,
                            focusedIndicatorColor = Color.Gray
                        ),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search, contentDescription = null
                            )
                        })
                }
               items(channels.value){
                   channel->Column {
                      ChannelItem(channel.name,
                          onClick = {
                              navControl?.navigate("chat/${channel.id}")
                          })


                      Spacer(Modifier.padding(8.dp))

               }
               }
            }


        }
        if(addchannel.value){
            ModalBottomSheet(onDismissRequest = {
                addchannel.value=false
            }, sheetState = sheetState) {
                AddChannelDialog {
                    viewModel.addChannel(it)
                    addchannel.value = false
                }
            }

        }
    }


}
@Composable
fun ChannelItem(channelName:String,onClick:()->Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Gray)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically

    ){
        Box(modifier = Modifier
            .padding(16.dp)

            .size(50.dp)
            .clip(CircleShape)
            .background(Color.Yellow.copy(alpha = 0.3f)),) {
            Text(
                "${channelName[0].toString()}",

                color = Color.White,
                style = TextStyle(fontSize = 35.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Text("${channelName}", modifier = Modifier.padding(8.dp))

    }
}

@Composable
fun AddChannelDialog(onAddChannel: (String)-> Unit){
    var channelName = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add channel")
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = channelName.value,
            label = {Text("channel name")},
            onValueChange = {channelName.value=it}, singleLine = true
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = {onAddChannel(channelName.value)},
            modifier = Modifier.fillMaxWidth()
            ) {
            Text("Add")
        }

    }

}

