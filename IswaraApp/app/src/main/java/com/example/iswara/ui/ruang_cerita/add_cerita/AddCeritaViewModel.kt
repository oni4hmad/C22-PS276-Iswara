package com.example.iswara.ui.ruang_cerita.add_cerita

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iswara.data.network.ApiConfig
import com.example.iswara.data.network.Cerita
import com.example.iswara.data.preferences.Session
import com.example.iswara.ui.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCeritaViewModel() : ViewModel() {

    companion object {
        private const val TAG = "AddCeritaViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _isSucceed = MutableLiveData<Event<Boolean>>()
    val isSucceed: LiveData<Event<Boolean>> = _isSucceed

    fun postStory(s: Session, date: String, cerita: String) {
        _isLoading.value = true
        val name = s.name as String
        val userId = s.id as String
        postStoryBaseUrlAll(name, date, cerita)
        postStoryBaseUrlUser(name, userId, date, cerita)
    }

    private fun postStoryBaseUrlAll(name: String, date: String, cerita: String) {
        val baseUrl = ApiConfig.ALL_CERITA_URL
        val client = ApiConfig.getApiService(baseUrl).postStory(name, date, cerita)
        client.enqueue(object : Callback<Cerita> {
            override fun onResponse(
                call: Call<Cerita>,
                response: Response<Cerita>
            ) {
//                _isLoading.value = false
                if (response.isSuccessful) {
                    // _isSucceed.value = Event(true)
                } else {
                    _toastText.value = Event(response.message())
                    Log.e(TAG, "onFailure x: ${response.message()}")
//                    response.errorBody()?.let {
//                        val jObjError = JSONObject(it.string())
//                        _toastText.value = Event(jObjError.getString("message"))
//                    } ?: let {
//                        _toastText.value = Event(response.message())
//                    }
                }
            }
            override fun onFailure(call: Call<Cerita>, t: Throwable) {
//                _isLoading.value = false
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure y: ${t.message}")
            }
        })

    }

    private fun postStoryBaseUrlUser(name: String, userId: String, date: String, cerita: String) {
        val baseUrl = ApiConfig.USER_CERITA_URL
        val client = ApiConfig.getApiService(baseUrl).postStoryWithUserId(name, userId, date, cerita)
        client.enqueue(object : Callback<Cerita> {
            override fun onResponse(
                call: Call<Cerita>,
                response: Response<Cerita>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isSucceed.value = Event(true)
                } else {
                    _toastText.value = Event(response.message())
                    Log.e(TAG, "onFailure x: ${response.message()}")
//                    response.errorBody()?.let {
//                        val jObjError = JSONObject(it.string())
//                        _toastText.value = Event(jObjError.getString("message"))
//                    } ?: let {
//                        _toastText.value = Event(response.message())
//                    }
                }
            }
            override fun onFailure(call: Call<Cerita>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure y: ${t.message}")
            }
        })
    }

}