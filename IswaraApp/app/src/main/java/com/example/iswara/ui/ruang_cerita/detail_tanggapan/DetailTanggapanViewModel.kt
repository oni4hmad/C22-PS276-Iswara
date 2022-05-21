package com.example.iswara.ui.ruang_cerita.detail_tanggapan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iswara.data.dummy.DataDummy

class DetailTanggapanViewModel : ViewModel() {

    private val _listTanggapan = MutableLiveData<List<TanggapanItem>>()
    val listTanggapan: LiveData<List<TanggapanItem>> = _listTanggapan

    init {
        /* populate data tanggapan */
        _listTanggapan.value = DataDummy.getListTanggapanItem(10)
    }


}