package com.example.iswara.ui.ruang_cerita.semua_cerita

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iswara.data.dummy.DataDummy
import com.example.iswara.ui.ruang_cerita.CeritaItem

class SemuaCeritaViewModel : ViewModel() {

    private val _listCerita = MutableLiveData<List<CeritaItem>>()
    val listCerita: LiveData<List<CeritaItem>> = _listCerita

    init {
        /* populate data cerita */
        _listCerita.value = DataDummy.getListCeritaItem(10)
    }

}