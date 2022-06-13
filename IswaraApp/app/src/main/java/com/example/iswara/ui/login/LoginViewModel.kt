package com.example.iswara.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iswara.data.network.ApiConfig
import com.example.iswara.data.network.User
import com.example.iswara.data.preferences.Session
import com.example.iswara.ui.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    companion object {
        private const val TAG = "LoginViewModel"
    }

    private val _session = MutableLiveData<Session>()
    val session: LiveData<Session> = _session

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    init {
        _isLoading.value = false
    }

    fun login(email: String) {
        _isLoading.value = true
        val baseUrl = ApiConfig.USER_CERITA_URL
        val client = ApiConfig.getApiService(baseUrl).getListUser(email)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { loginResponse ->
                        if (loginResponse.isNotEmpty()) {
                            loginResponse[0].let {
                                _session.value = Session(it.id, it.name, it.email, it.phoneNum)
                            }
                        } else {
                            _toastText.value = Event("User not found")
                        }
                    }

                } else {
                    Log.e(TAG, "onFailure x: ${response.message()}")
//                    response.errorBody()?.let {
//                        val jObjError = JSONObject(it.string())
//                        _toastText.value = Event(jObjError.getString("message"))
//                    } ?: let {
//                        _toastText.value = Event(response.message())
//                    }
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure y: ${t.message}")
            }
        })
    }

}