package com.example.iswara.ui.settings_laporanku

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class LaporanItem(
    val idLaporan: String,
    val date: Date
) : Parcelable
