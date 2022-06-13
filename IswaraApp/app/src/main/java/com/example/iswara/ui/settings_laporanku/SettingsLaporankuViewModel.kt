package com.example.iswara.ui.settings_laporanku

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.iswara.data.database.Report
import com.example.iswara.data.preferences.Session
import com.example.iswara.di.Injection
import com.example.iswara.repository.ReportRepository

class SettingsLaporankuViewModelFactory(private val context: Context, private val session: Session): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsLaporankuViewModel(Injection.provideRepository(context), session) as T
    }
}

class SettingsLaporankuViewModel(private val reportRepository: ReportRepository, private val session: Session) : ViewModel() {

    private val userId get() = session.id ?: "-1"

    init {
        /* populate laporan */
        // _listLaporan.value = DataDummy.getListLaporan(3)
    }

    fun getAllReportByUser(): LiveData<List<Report>> = reportRepository.getAllReportByUserId(userId)

}