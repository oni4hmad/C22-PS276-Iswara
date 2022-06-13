package com.example.iswara.data.preferences

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Session (
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var phoneNum: String? = null
) : Parcelable