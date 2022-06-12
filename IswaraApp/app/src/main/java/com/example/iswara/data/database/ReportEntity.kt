package com.example.iswara.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Report(
    @PrimaryKey(autoGenerate = true)
    val reportId: Int,
    val userId: String,
    val date: String,
    val isFinish: Boolean
)

@Entity
data class Chat(
    @PrimaryKey(autoGenerate = true)
    val chatId: Int,
    val repId: Int,
    val date: String,
    val isUser: Boolean,
    val chat: String
)

@Entity
data class BotState(
    @PrimaryKey
    val repId: Int,
    val remainClassJson: String,
    val isEnded: Boolean
)

data class ReportAndChat(
    @Embedded
    val report: Report,

    @Relation(
        parentColumn = "reportId",
        entityColumn = "repId"
    )
    val chat: Chat? = null
)