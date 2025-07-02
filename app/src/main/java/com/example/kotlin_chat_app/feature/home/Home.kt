package com.example.kotlin_chat_app.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kotlin_chat_app.models.Channel
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen_Home (navControl: NavHostController){
    val viewModel = hiltViewModel<HomeviewModel>()
    val channels = viewModel.channels.collectAsState()
    val addchannel = remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    Scaffold (
            floatingActionButton = {
                Box(
                    modifier = Modifier.padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Blue)
                        .clickable{
                            addchannel.value=true
                        }
                ){
                    Text("Add channel",
                    modifier = Modifier.padding(16.dp), color = Color.White
                        )


                }

            }


    ){innerPadding ->
        Box (
            modifier = Modifier.padding(innerPadding).fillMaxSize().background(Color.White  )
        ){
            LazyColumn (){
               items(channels.value){
                   channel->Column {
                       Text(channel.name,
                           modifier = Modifier.padding(8.dp).
                           fillMaxWidth().
                           clip(RoundedCornerShape(16.dp)).
                           background(Color.Gray).padding(16.dp)
                               .clickable{navControl?.navigate("chat/${channel.id}") }
                           )
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
//@Preview(showSystemUi = true)
//@Composable
//fun showUIHome(){
//    Screen_Home(navControl = null)
//}

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