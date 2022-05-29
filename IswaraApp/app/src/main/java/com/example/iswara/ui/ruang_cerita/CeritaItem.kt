package com.example.iswara.ui.ruang_cerita

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class CeritaItem(
    val idCerita: String,
    var name: String,
    val date: Date,
    val cerita: String,
    val tanggapanCount: Int,
    val supportCount: Int
) : Parcelable
