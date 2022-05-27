package com.example.iswara.ui.ruang_cerita.detail_tanggapan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iswara.data.dummy.DataDummy
import java.util.*
import kotlin.collections.ArrayList

class DetailTanggapanViewModel : ViewModel() {

    private val _listTanggapan = MutableLiveData<ArrayList<TanggapanItem>>()
    val listTanggapan: LiveData<ArrayList<TanggapanItem>> = _listTanggapan

    init {
        /* populate data tanggapan */
        _listTanggapan.value = DataDummy.getListTanggapanItem(10)
    }

    fun addTanggapan(text: String) {
        val id = _listTanggapan.value?.count().toString()
        val tanggapanBaru = TanggapanItem(id, "Oni", Date(), text)
        _listTanggapan.value?.let { list ->
            list.add(tanggapanBaru)
            _listTanggapan.value = list
        } ?: run {
            val temp = ArrayList<TanggapanItem>()
            temp.add(tanggapanBaru)
            _listTanggapan.value = temp
        }
    }

}