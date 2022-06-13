package com.example.iswara.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iswara.data.network.ApiConfig
import com.example.iswara.data.network.User
import com.example.iswara.ui.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    companion object {
        private const val TAG = "RegisterViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isRegisterSuccess = MutableLiveData<Boolean>()
    val isRegisterSuccess: LiveData<Boolean> = _isRegisterSuccess

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    init {
        _isLoading.value = false
    }

    fun register(name: String, email: String, password: String, phoneNum: String) {
        _isLoading.value = true
        val baseUrl = ApiConfig.USER_CERITA_URL
        val client = ApiConfig.getApiService(baseUrl).postUser(name, email, password, phoneNum)
        client.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _isRegisterSuccess.value = true
//                        if (registerResponse.error) {
//                            _toastText.value = Event(registerResponse.message)
//                        } else {
//                            _isRegisterSuccess.value = true
//                        }
                    }

                } else {
                    Log.e(TAG, "onFailure x: ${response.message()}")
                    _toastText.value = Event(response.toString())
//                    response.errorBody()?.let {
//                        val jObjError = JSONObject(it.string())
//                        _toastText.value = Event(jObjError.getString("message"))
//                    } ?: let {
//                        _toastText.value = Event(response.message())
//                    }
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure y: ${t.message}")
            }
        })
    }
}