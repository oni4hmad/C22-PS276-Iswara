package com.example.iswara.ui.ruang_cerita.detail_tanggapan

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class TanggapanItem(
    val idTanggapan: String,
    val name: String,
    val date: Date,
    val tanggapan: String
) : Parcelable
