package com.example.iswara.ui.ruang_cerita.detail_tanggapan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iswara.data.network.ApiConfig
import com.example.iswara.data.network.Cerita
import com.example.iswara.data.network.Tanggapan
import com.example.iswara.ui.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTanggapanViewModel : ViewModel() {

    companion object {
        private const val TAG = "Tanggapan"
    }

    private val _listTanggapan = MutableLiveData<List<Tanggapan>>()
    val listTanggapan: LiveData<List<Tanggapan>> = _listTanggapan

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _isSucceed = MutableLiveData<Event<Boolean>>()
    val isSucceed: LiveData<Event<Boolean>> = _isSucceed

    private lateinit var cerita: Cerita

    init {
        /* populate data tanggapan */
        // _listTanggapan.value = DataDummy.getListTanggapanItem(10)
    }

    fun setCerita(cerita: Cerita) {
        this.cerita = cerita
    }

    fun getTanggapan(page: Int = 1, size: Int = 20, callback: (() -> Unit)? = null) {
        _isLoading.value = true
        val baseUrl = ApiConfig.ALL_CERITA_URL
        val client = ApiConfig.getApiService(baseUrl).getListTanggapan(cerita.idCerita, page, size)
        client.enqueue(object : Callback<List<Tanggapan>> {
            override fun onResponse(
                call: Call<List<Tanggapan>>,
                response: Response<List<Tanggapan>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback?.invoke()
                        _listTanggapan.value = it
                    }

                } else {
                    Log.e(TAG, "onFailure x: ${response.message()}")
//                    response.errorBody()?.let {
//                        val jObjError = JSONObject(it.string())
//                        _error.value = Error(true, jObjError.getString("message"))
//                    } ?: let {
//                        _error.value = Error(true, response.message())
//                    }
                }
            }
            override fun onFailure(call: Call<List<Tanggapan>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure y: ${t.message}")
            }
        })
    }

    fun postTanggapan(name: String, date: String, tanggapan: String) {
        _isLoading.value = true
        val baseUrl = ApiConfig.ALL_CERITA_URL
        val client = ApiConfig.getApiService(baseUrl).postTanggapan(name, date, tanggapan, cerita.idCerita)
        client.enqueue(object : Callback<Tanggapan> {
            override fun onResponse(
                call: Call<Tanggapan>,
                response: Response<Tanggapan>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isSucceed.value = Event(true)
                } else {
                    _toastText.value = Event(response.message())
                    Log.e(TAG, "onFailure x: ${response.message()}")
//                    response.errorBody()?.let {
//                        val jObjError = JSONObject(it.string())
//                        _error.value = Error(true, jObjError.getString("message"))
//                    } ?: let {
//                        _error.value = Error(true, response.message())
//                    }
                }
            }
            override fun onFailure(call: Call<Tanggapan>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure y: ${t.message}")
            }
        })
    }

}