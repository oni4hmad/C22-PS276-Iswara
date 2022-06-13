package com.example.iswara.data.preferences

import android.content.Context

internal class SessionPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "session_pref"
        private const val USER_ID = "userId"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val PHONE_NUM = "phoneNum"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setSession(value: Session) {
        val editor = preferences.edit()
        editor.apply {
            putString(USER_ID, value.id)
            putString(NAME, value.name)
            putString(EMAIL, value.email)
            putString(PHONE_NUM, value.phoneNum)
            apply()
        }
    }

    fun getSession(): Session? {
        val model = Session()
        model.apply {
            id = preferences.getString(USER_ID, null)
            name = preferences.getString(NAME, null)
            email = preferences.getString(EMAIL, null)
            phoneNum = preferences.getString(PHONE_NUM, null)
        }
        return if (model.id.isNullOrEmpty()) {
            null
        } else {
            model
        }
    }

    fun getUserId(): String? =
        getSession()?.id

    fun clearSession() {
        val nullSession = Session()
        setSession(nullSession)
    }
}