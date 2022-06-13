package com.example.iswara.di

import android.content.Context
import com.example.iswara.data.database.ReportDatabase
import com.example.iswara.repository.ReportRepository

object Injection {
    fun provideRepository(context: Context): ReportRepository {
        val database = ReportDatabase.getDatabase(context)
        return ReportRepository(database.reportDao())
    }
}