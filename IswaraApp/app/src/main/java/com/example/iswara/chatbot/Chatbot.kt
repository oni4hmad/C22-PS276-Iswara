package com.example.test_tflite_app_simple.chatbot

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.Charset

class Chatbot (private val context: Context) {

    private lateinit var tflite: Interpreter

    /* debug purpose */
    var makeEndedRespondLast = true
    var allowLoop = false

    private val modelFIleName = "iswara_model.tflite"
    private val intentsFileName = "intents.json"
    private val classesFileName = "classes.json"
    private val wordsFileName = "words.json"

    private var botIntents: BotIntent
    private var endIntentClasses = ArrayList<String>()
    private var remainingIntentClasses = ArrayList<String>()

    private var classesList: ArrayList<String>? = null
    private var wordsList: ArrayList<String>? = null

    private var isBotSessionEnded = false

    private var onChatbotResponded: ((String, Input, InputFormat) -> Unit)? = null

    init {
        /* init intents, classes, and words list */
        botIntents = getBotIntent(intentsFileName)
        botIntents.listIntent.forEach {
            if (it.input.type == Input.ENDED.value) {
                endIntentClasses.add(it.tag)
            }
        }
        classesList = getRawJson(context, classesFileName)?.let { rawJson ->
            rawJsonToArrayList(rawJson).onEach {
                val isNotEndedClass = !isExistInList(it, endIntentClasses)
                if (isNotEndedClass) {
                    remainingIntentClasses.add(it)
                }
            }
        }
        wordsList = getRawJson(context, wordsFileName)?.let {
            rawJsonToArrayList(it)
        }

        /* init model interpreter */
        try {
            tflite = Interpreter(loadModelFile(modelFIleName))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun chat(userChat: String) {

        val prediction = preprocessingAndPredict(userChat)
        Log.d("prediction", prediction?.joinToString().toString())

        /* bind prediction and get bot response */
        val predictionMap = classesList?.let {
            mapPrediction(prediction, it).toList().sortedByDescending { (_, value) -> value}.toMap()
        }
        Log.d("predictionMap", predictionMap.toString())

        if (!isBotSessionEnded) predictionMap?.entries?.also { entries ->
            if (!allowLoop) {
                for (classPrediction in entries) {
                    val isRemainingClassesEmpty = remainingIntentClasses.isEmpty()
                    val isExistInRemainingClasses = isExistInList(classPrediction.key, remainingIntentClasses)
                    val isThisEntryEndedClass = isExistInList(classPrediction.key, endIntentClasses)

                    if (isExistInRemainingClasses && !isThisEntryEndedClass || !makeEndedRespondLast) {

                        showToast("tag: ${classPrediction.key}")

                        remainingIntentClasses.remove(classPrediction.key)

                        val botResponse = getBotResponseRandomize(classPrediction.key)
                        val userInput = getUserInput(classPrediction.key)

                        if (!botResponse.isNullOrEmpty() && userInput != null) {
                            val inputType = Input.fromInt(userInput.type)
                            onChatbotResponded?.invoke(botResponse, inputType, userInput)
                            Log.d("predictionMap 2 input", "${inputType}, $userInput")
                        }

                        Log.d("predictionMap 1 top", "key=${classPrediction.key}, value=${classPrediction.value}")
                        break
                    } else if (isRemainingClassesEmpty && isThisEntryEndedClass) {

                        showToast("tag: ${classPrediction.key}")

                        val botResponse = getBotResponseRandomize(classPrediction.key)
                        val userInput = getUserInput(classPrediction.key)

                        if (!botResponse.isNullOrEmpty() && userInput != null) {
                            val inputType = Input.fromInt(userInput.type)
                            onChatbotResponded?.invoke(botResponse, inputType, userInput)
                            Log.d("predictionMap 2 input", "${inputType}, $userInput")
                        }

                        isBotSessionEnded = true

                        Log.d("predictionMap 2 top", "key=${classPrediction.key}, value=${classPrediction.value}")
                        break
                    }
                }
            } else {
                for (classPrediction in entries) {
                    val isThisEntryEndedClass = isExistInList(classPrediction.key, endIntentClasses)

                    if (!isThisEntryEndedClass || !makeEndedRespondLast) {
                        val botResponse = getBotResponseRandomize(classPrediction.key)
                        val userInput = getUserInput(classPrediction.key)

                        if (!botResponse.isNullOrEmpty() && userInput != null) {
                            val inputType = Input.fromInt(userInput.type)
                            onChatbotResponded?.invoke(botResponse, inputType, userInput)
                            Log.d("predictionMap 3 input", "${inputType}, $userInput")
                        }
                        Log.d("predictionMap 3 top", "key=${classPrediction.key}, value=${classPrediction.value}")
                        break
                    }
                }
            }
        }
    }

    fun setOnChatbotResponded(callback: (String, Input, InputFormat) -> Unit) {
        this.onChatbotResponded = callback
    }

    /*
    * Reset remaining intents, so all bot responses can be called again
    * */
    fun resetSession() {
        isBotSessionEnded = false
        remainingIntentClasses.clear()
        classesList?.forEach {
            val isNotEndedClass = !isExistInList(it, endIntentClasses)
            if (isNotEndedClass) {
                remainingIntentClasses.add(it)
            }
        }
    }

    private fun isExistInList(el: String, list: ArrayList<String>): Boolean {
        list.forEach {
            if (it == el)
                return true
        }
        return false
    }

    private fun getBotResponseRandomize(intentTag: String): String? {
        val responses = botIntents.listIntent.firstOrNull { it.tag == intentTag }?.listReponses
        return responses?.random()
    }

    private fun getUserInput(intentTag: String): InputFormat? {
        return botIntents.listIntent.firstOrNull { it.tag == intentTag }?.input
    }

    @Throws(IOException::class)
    private fun loadModelFile(fileName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(fileName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel: FileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declareLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declareLength)
    }

    private fun preprocessingAndPredict(chat: String): FloatArray? {
        val chatWords = cleanUpSentence(chat)
        val processedFeature = wordsList?.let {
            bagOfWords(chatWords, it)
        }
        return processedFeature?.let {
            doInference(processedFeature)
        }
    }

    private fun doInference(processedFeature: FloatArray): FloatArray {
        val outputSize = classesList?.size ?: 0
        val output = Array(1) {
            FloatArray(outputSize)
        }
        tflite.run(processedFeature, output)
        return output[0]
    }

    private fun mapPrediction(prediction: FloatArray?, classList: ArrayList<String>): HashMap<String, Float> {
        val hashMap = HashMap<String, Float>()
        prediction?.forEachIndexed { index, item ->
            hashMap.put(classList[index], item)
        }
        return hashMap
    }

    private fun getBotIntent(fileName: String): BotIntent {
        val gson = Gson()
        val jsonIntentsStr = getRawJson(context, fileName)
        Log.d("jsonIntentsStr", jsonIntentsStr.toString())
        val botIntent: BotIntent = gson.fromJson(jsonIntentsStr, BotIntent::class.java)
        Log.d("botIntent", botIntent.toString())
        return botIntent
    }

    /*
    * return bag of words array: 0 or 1 for words that exist in wordList
    * */
    private fun bagOfWords(chatWords: ArrayList<String>, wordList: ArrayList<String>): FloatArray {
        val size = wordList.size
        val bag = FloatArray(size)
        chatWords.forEach { chatWord ->
            wordList.forEachIndexed { index, wordItem ->
                if (chatWord == wordItem) {
                    bag[index] = 1F
                    Log.d("bow loop", "sama!")
                }
            }
        }
        return bag
    }

    private fun cleanUpSentence(chat: String): ArrayList<String> {
        // lowercasing + tokenize using regex
        val text = chat.lowercase()
        val regex = """(\w+|\d|[^\s-])""".toRegex()
        val matches = regex.findAll(text)
        val arr = ArrayList<String>()
        matches.forEach { arr.add(it.groupValues[0]) }

        // lemmatizer
        /* gatau implementasinya di kotlin/java */

        return arr
    }

    private fun getRawJson(context: Context, fileName: String): String? {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun rawJsonToArrayList(jsonObjStr: String): ArrayList<String> {
        val gson = GsonBuilder().create()
        return gson.fromJson(jsonObjStr, object : TypeToken<ArrayList<String>>() {}.type)
    }

    enum class Input(val value: Int) {
        ENDED(-1),
        CHAT(0),
        RADIO_BUTTON(1),
        CHECK_BOX(2);

        companion object {
            private val map: MutableMap<Int, Input> = HashMap()
            init {
                for (i in values())
                    map[i.value] = i
            }
            fun fromInt(type: Int?): Input {
                return map[type] ?: CHAT
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(context,text, Toast.LENGTH_SHORT).show()
    }
}