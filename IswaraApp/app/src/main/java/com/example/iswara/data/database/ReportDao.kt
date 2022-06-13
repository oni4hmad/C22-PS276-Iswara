package com.example.iswara.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReportDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReport(report: Report)

    @Update
    suspend fun updateReport(report: Report)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChat(chat: Chat)

    @Delete
    suspend fun deleteReport(report: Report)

    @Query("SELECT * FROM report ORDER BY date DESC")
    fun getAllReport(): LiveData<List<Report>>

    @Query("SELECT * FROM report WHERE userId = :userId ORDER BY date DESC")
    fun getAllReportByUserId(userId: String): LiveData<List<Report>>

    @Query("SELECT * FROM report WHERE userId = :userId AND isFinish = 0 ORDER BY date DESC LIMIT 1")
    fun getAllOnGoingReportByUserId(userId: String): LiveData<Report?>

    @Query("SELECT * FROM chat WHERE repId = :reportId ORDER BY date ASC")
    fun getAllChatByReportId(reportId: Int): LiveData<List<Chat>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBotState(botState: BotState)

    @Update
    suspend fun updateBotState(botState: BotState)

    @Query("SELECT * FROM botstate WHERE repId = :reportId LIMIT 1")
    fun getBotStateByReportId(reportId: Int): BotState?
}