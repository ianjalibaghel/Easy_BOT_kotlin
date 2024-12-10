package com.example.bot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bot.ui.theme.Pink40
import com.example.bot.ui.theme.modelcolor
import com.example.bot.ui.theme.usercolor

@Composable
fun ChatPage(modifier: Modifier = Modifier,viewModel: ChatViewModel) {
    Column(
        modifier=Modifier.fillMaxSize()
    ) {
        Headertext()

        MessageList(
            messageList = viewModel.messageList,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()

        )
        MessageInput(onmessagesend = {
            viewModel.sendMessage(it)
        }
        )
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier, messageList : List<MessageModel>) =
    if(messageList.isEmpty()){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
             Icon(modifier = Modifier.size(60.dp),painter = painterResource(id = R.drawable.baseline_question_answer_24),
                 contentDescription ="icon" , tint = Pink40)
             Text(text = "Ask me Anything", fontSize = 24.sp)
         }
    }else{
        LazyColumn(
            modifier = Modifier,
            reverseLayout = true
        ) {
            items(messageList.reversed()){
                messageRow(messageModel = it)
            }
        }
    }

@Composable
fun messageRow(messageModel: MessageModel) {
    val isModel =messageModel.role =="Model"
    Row (verticalAlignment = Alignment.CenterVertically){
        Box(modifier = Modifier.fillMaxWidth()){
            Box(modifier = Modifier
                .align(if (isModel) Alignment.BottomStart else Alignment.BottomEnd)
                .padding(
                    start = if (isModel) 8.dp else 70.dp,
                    end = if (isModel) 70.dp else 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
                .clip(RoundedCornerShape(48f))
                .background(if (isModel) modelcolor else usercolor)
                .padding(16.dp)
            ){
                SelectionContainer {
                    Text(text = messageModel.message, fontWeight = FontWeight(500))
                }
            }
        }
    }
}

@Composable
fun MessageInput(onmessagesend: (String)->Unit) {
    var msg by rememberSaveable {
       mutableStateOf("") 
    }
    Row (modifier = Modifier
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically){
        OutlinedTextField(
            value = msg,
            label = { Text(text = "Ask anything") },
            modifier = Modifier.weight(1f),
            onValueChange = {
                msg = it
            }
        )
        IconButton(
            onClick = {
                if(msg.isNotEmpty()){
                onmessagesend(msg)
            msg=""}
        }) {
            Icon(imageVector = Icons.Default.Send, contentDescription ="Send" )
        }
    }
}

@Composable
fun Headertext() {
    Box(
        modifier= Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ){
        Text(
            text = "Talk Bot",
            modifier = Modifier.padding(16.dp),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
