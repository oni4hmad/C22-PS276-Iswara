package com.example.iswara.ui.ruang_cerita.detail_tanggapan

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class TanggapanItem(
    var idTanggapan: String,
    var name: String,
    var date: Date,
    var tanggapan: String
) : Parcelable
