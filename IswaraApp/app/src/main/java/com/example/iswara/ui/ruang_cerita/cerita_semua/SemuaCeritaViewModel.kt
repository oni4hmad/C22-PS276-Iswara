package com.example.iswara.ui.ruang_cerita.cerita_semua

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iswara.data.network.ApiConfig
import com.example.iswara.data.network.Cerita
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SemuaCeritaViewModel : ViewModel() {

    companion object {
        private const val TAG = "SemuaCeritaViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listCerita = MutableLiveData<List<Cerita>>()
    val listCerita: LiveData<List<Cerita>> = _listCerita

    private val _error = MutableLiveData<Error>()
    val error: LiveData<Error> = _error

    init {
        /* populate data cerita */
        // _listCerita.value = DataDummy.getListCeritaItem(10)
        getStory(1, 10)
    }

    fun getStory(page: Int = 1, size: Int = 10) {
        _isLoading.value = true
        val baseUrl = ApiConfig.ALL_CERITA_URL
        val client = ApiConfig.getApiService(baseUrl).getListCerita(page, size)
        client.enqueue(object : Callback<List<Cerita>> {
            override fun onResponse(
                call: Call<List<Cerita>>,
                response: Response<List<Cerita>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.isEmpty()) {
                            _error.value = Error(true, type = ErrorType.NO_DATA)
                            return
                        }
                        _error.value = Error(false)
                        _listCerita.value = it
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
            override fun onFailure(call: Call<List<Cerita>>, t: Throwable) {
                _isLoading.value = false
                _error.value = Error(true, t.message.toString())
                Log.e(TAG, "onFailure y: ${t.message}")
            }
        })
    }

    inner class Error(
        val isError: Boolean,
        val errorMsg: String? = null,
        val type: ErrorType? = null
    )

    enum class ErrorType {
        NO_DATA
    }

}