package com.example.iswara.ui.settings_laporanku

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iswara.data.dummy.DataDummy

class SettingsLaporankuViewModel : ViewModel() {

    private val _listLaporan = MutableLiveData<List<LaporanItem>>()
    val listLaporan: LiveData<List<LaporanItem>> = _listLaporan

    init {
        /* populate laporan */
        _listLaporan.value = DataDummy.getListLaporan(3)
    }

}