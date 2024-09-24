package com.example.bot

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel :ViewModel() {
    val messageList by lazy{
        mutableStateListOf<MessageModel>()
    }

    val generativeModel =
        GenerativeModel(
            // Specify a Gemini model appropriate for your use case
            modelName = "gemini-1.5-flash",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = Constants.apiKey)

    fun sendMessage(question : String){
        viewModelScope.launch {
            try {
                val chat= generativeModel.startChat(
                    history = messageList.map {
                        content(it.role){text(it.message)}
                    }.toList()
                )
                messageList.add(MessageModel(question,"User"))
                messageList.add(MessageModel("Typing..","Model"))

                val response = chat.sendMessage(question)
                messageList.removeLast()
                messageList.add(MessageModel(response.text.toString(),"Model"))
            }catch (e: Exception){
                messageList.removeLast()
                messageList.add(MessageModel("Error :" + e.message.toString(),"Model"))
            }

        }
    }
}