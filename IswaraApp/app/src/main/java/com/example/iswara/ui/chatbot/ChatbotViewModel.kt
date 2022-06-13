package com.example.iswara.ui.chatbot

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.iswara.data.database.BotState
import com.example.iswara.data.database.Chat
import com.example.iswara.data.database.Report
import com.example.iswara.data.dummy.DataDummy
import com.example.iswara.data.preferences.Session
import com.example.iswara.di.Injection
import com.example.iswara.repository.ReportRepository
import com.example.iswara.ui.ruang_cerita.cerita_user.UserCeritaViewModel
import com.example.iswara.ui.ruang_cerita.detail_tanggapan.TanggapanItem
import com.example.iswara.utils.dateToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class ChatbotViewModelModelFactory(private val context: Context, private val session: Session): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatbotViewModel(Injection.provideRepository(context), session) as T
    }
}

class ChatbotViewModel(private val reportRepository: ReportRepository, private val session: Session) : ViewModel() {

    companion object {
        private const val TAG = "ChatbotViewModel"
    }

//    private val _listChat = MutableLiveData<ArrayList<ChatItem>>()
//    val listChat: LiveData<ArrayList<ChatItem>> = _listChat

    private val userId get() = session.id ?: ""
    private var report: Report? = null
    private val reportId get() = report?.reportId ?: -1

    init {
        /* get dummy chat */
        // _listChat.value = DataDummy.getChat(false, 1)
    }

//    private fun generateBalasan() {
//        val id = _listChat.value?.count().toString()
//        DataDummy.getChat(false, 1)[0].let {
//            _listChat.value?.let { list ->
//                list.add(it)
//                _listChat.value = list
//            }
//        }
//    }

    fun setReport(report: Report) {
        this.report = report
    }

    fun getOnGoingReport(): LiveData<Report?> = reportRepository.getOnGoingReportByUserId(userId)

    suspend fun addReport(callback: (() -> Unit)? = null) {
        var newReport = Report(0, userId, dateToString(Date()), false)
        reportRepository.insertReport(newReport)
        this.report = newReport
        callback?.invoke()
    }

    fun getChatHistory(): LiveData<List<Chat>> {
        Log.d(TAG, reportId.toString())
        return reportRepository.getAllChatByReportId(reportId)
    }

    suspend fun sendUserChat(chat: String) {
//        val id = _listChat.value?.count().toString()
//        val chatBaru = ChatItem(id, true, chat)
//        _listChat.value?.let { list ->
//            list.add(chatBaru)
//            _listChat.value = list
//        } ?: run {
//            val temp = ArrayList<ChatItem>()
//            temp.add(chatBaru)
//            _listChat.value = temp
//        }
//        generateBalasan()

        report?.reportId?.also { repId ->
            val newChat = Chat(0, repId, dateToString(Date()), true, chat)
            reportRepository.insertChat(newChat)
        }
    }

    suspend fun sendBotChat(chat: String) {
//        val id = _listChat.value?.count().toString()
//        val chatBaru = ChatItem(id, false, chat)
//        _listChat.value?.let { list ->
//            list.add(chatBaru)
//            _listChat.value = list
//        } ?: run {
//            val temp = ArrayList<ChatItem>()
//            temp.add(chatBaru)
//            _listChat.value = temp
//        }
//        generateBalasan()

        report?.reportId?.also { repId ->
            val newChat = Chat(0, repId, dateToString(Date()), false, chat)
            reportRepository.insertChat(newChat)
        }
    }

    suspend fun insertOrUpdateBotState(jsonState: String) {
        val botState = reportRepository.getBotStateByReportId(reportId)
        botState?.let {
            val state = BotState(reportId, jsonState, false)
            reportRepository.updateBotState(state)
        } ?: run {
            val state = BotState(reportId, jsonState, false)
            reportRepository.insertBotState(state)
        }
    }

    suspend fun setBotStateEnded() {
        val botState = reportRepository.getBotStateByReportId(reportId)
        botState?.let {
            val state = BotState(reportId, botState.remainClassJson, true)
            reportRepository.updateBotState(state)
        }
    }

    fun getBotState(): BotState? = reportRepository.getBotStateByReportId(reportId)

}