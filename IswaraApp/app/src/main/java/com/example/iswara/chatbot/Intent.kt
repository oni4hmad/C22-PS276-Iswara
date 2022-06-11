package com.example.test_tflite_app_simple.chatbot

import com.google.gson.annotations.SerializedName

data class BotIntent(
    @SerializedName("intents")
    var listIntent: List<IntentItem>
)

data class IntentItem (
    @SerializedName("tag")
    var tag: String,
    @SerializedName("responses")
    var listReponses: List<String>,
    @SerializedName("input")
    var input: InputFormat
)

data class InputFormat (
    @SerializedName("type")
    var type: Int,
    @SerializedName("title")
    var title: String?,
    @SerializedName("options")
    var options: List<String>?
)
