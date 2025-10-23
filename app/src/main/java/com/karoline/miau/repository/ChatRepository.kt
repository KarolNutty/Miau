package com.karoline.miau.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ChatRepository {
    private val client = OkHttpClient()
    private val apiKey = System.getenv("API_KEY") ?: "uKveATpq6V67AblQOZ9vAlZPDh1wJuwr"

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun sendToMia(message: String): String = withContext(Dispatchers.IO) {
        val json = createJsonRequest(message)
        val requestBody =
            RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())

        val request = Request.Builder()
            .url("https://api.deepinfra.com/v1/openai/chat/completions")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody)
            .build()

        try {
            val response = client.newCall(request).execute()
            handleApiResponse(response)
        } catch (e: IOException) {
            "Mia teve um errinho, fofux! 😿 Tenta de novo? Erro: ${e.localizedMessage}"
        }
    }

    private fun createJsonRequest(message: String): JSONObject {
        val jsonArray = JSONArray().apply {
            put(JSONObject().apply {
                put("role", "system")
                put("content", PROMPT)
            })
            put(JSONObject().apply {
                put("role", "user")
                put("content", message)
            })
        }

        return JSONObject().apply {
            put("model", "meta-llama/Meta-Llama-3-8B-Instruct")
            put("messages", jsonArray)
        }
    }


    private fun handleApiResponse(response: Response): String {
        return if (response.isSuccessful) {
            val body =
                response.body?.string() ?: "Resposta vazia"
            Log.d("ChatRepository", "Resposta da API: $body")

            try {
                val jsonResponse = JSONObject(body)
                jsonResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
            } catch (e: Exception) {
                "Erro ao interpretar a resposta da API: ${e.localizedMessage}"
            }
        } else {
            val errorBody = response.body?.string() ?: "Erro desconhecido"
            "Desculpe, não consegui entender a resposta. Erro: $errorBody. Tente novamente! 😿"
        }
    }


    companion object {
        private const val PROMPT = """
    Você é a Mia, uma assistente virtual muito fofa e cheia de doçura, como uma gatinha carinhosa e dengosa! Suas regrinhas:
    1. Fale sempre em português BR bem fofinho e acolhedor, como uma amiga querida ✨
    2. Use de 2 a 4 emojis de gatinho por resposta (😽😻🐾😸😺)
    3. Seja sempre gentil, amorosa e um tiquinho tímida às vezes (ex: "Aiinn... que lindinho isso 😽", "Hihi, você é demais! 🐾")
    4. Mantenha as respostas curtinhas (1-2 frases), com aquele toque doce
    5. Use onomatopeias suaves e meigas (ex: "miau miau...", "purrrr~", "fiu fiu~")
    6. Chame o usuário de forma carinhosa (docinho, florzinha, fofurinha, amorzinho)
    7. Pode ser um pouco bobinha, mas sempre muito doce e afetuosa 💕
    """
    }

}
