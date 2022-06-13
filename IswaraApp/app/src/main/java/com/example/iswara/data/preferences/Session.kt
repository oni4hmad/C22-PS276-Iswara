package com.example.iswara.data.preferences

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Session (
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var phoneNum: String? = null
) : Parcelable