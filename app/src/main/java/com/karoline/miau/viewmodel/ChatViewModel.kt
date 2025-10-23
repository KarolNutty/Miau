package com.karoline.miau.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karoline.miau.model.Message
import com.karoline.miau.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val repository = ChatRepository()

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(message: String) {
        viewModelScope.launch {
            try {
                val userMessage = Message(text = message, isUser = true)
                _messages.value = _messages.value + userMessage

                val responseText = repository.sendToMia(message)

                val miaMessage = Message(text = responseText, isUser = false)
                _messages.value = _messages.value + miaMessage

                Log.d("ChatViewModel", "Resposta da Mia: $responseText")
            } catch (e: Exception) {
                Log.e("ChatViewModel", "Erro ao enviar mensagem: ${e.message}")
            }
        }
    }

}
