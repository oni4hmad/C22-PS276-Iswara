package com.example.iswara.repository

import androidx.lifecycle.LiveData
import com.example.iswara.data.database.BotState
import com.example.iswara.data.database.Chat
import com.example.iswara.data.database.Report
import com.example.iswara.data.database.ReportDao

class ReportRepository(private val reportDao: ReportDao) {

    suspend fun insertReport(report: Report) = reportDao.insertReport(report)
    suspend fun insertChat(chat: Chat) = reportDao.insertChat(chat)

    fun getAllReport(): LiveData<List<Report>> = reportDao.getAllReport()
    fun getAllReportByUserId(userId: String): LiveData<List<Report>> = reportDao.getAllReportByUserId(userId)
    fun getOnGoingReportByUserId(userId: String): LiveData<Report?> = reportDao.getAllOnGoingReportByUserId(userId)
    fun getAllChatByReportId(reportId: Int): LiveData<List<Chat>> = reportDao.getAllChatByReportId(reportId)

    suspend fun insertBotState(botState: BotState) = reportDao.insertBotState(botState)
    suspend fun updateBotState(botState: BotState) = reportDao.updateBotState(botState)
    fun getBotStateByReportId(reportId: Int): BotState? = reportDao.getBotStateByReportId(reportId)

    suspend fun updateReport(report: Report) = reportDao.updateReport(report)

}