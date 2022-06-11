package com.example.iswara.data.dummy

import android.util.Log
import com.example.iswara.ui.chatbot.ChatItem
import com.example.iswara.ui.ruang_cerita.CeritaItem
import com.example.iswara.ui.ruang_cerita.detail_tanggapan.TanggapanItem
import com.example.iswara.ui.settings_laporanku.LaporanItem
import io.github.serpro69.kfaker.Faker
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

object DataDummy {

    private fun randomNum(min: Int, max: Int): Int = (min..max).random()

    private fun getCalendarDate(): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, 2022)
            set(Calendar.MONTH, Calendar.MAY)
            set(Calendar.DAY_OF_MONTH, 17)
        }
    }

    private fun getRandomText(minSentence: Int = 1, maxSentence: Int): String {
        val faker = Faker()
        var text = String()
        val randSentenceCount = randomNum(minSentence, maxSentence).absoluteValue
        for (i in 1..randSentenceCount) {
            text += "${faker.quote.famousLastWords()} "
        }
        return text
    }

    fun getListCeritaItem(size: Int, nama: String? = null): ArrayList<CeritaItem> {
        val faker = Faker()
        val data = ArrayList<CeritaItem>()
        val cal = getCalendarDate()
        return data.apply {
            for (i in 1..size) {
                val id = "$i"
                val name = if (nama.isNullOrEmpty()) faker.name.nameWithMiddle() else nama
                val date = cal.time
                val cerita = getRandomText(1, 25)
                val tanggapanCount = randomNum(1, 999)
                val supportCount = randomNum(1, 999)
                Log.d("faker_cerita", "$id, $name, $date, $cerita, $tanggapanCount, $supportCount")
                add(CeritaItem(id, name, date, cerita, tanggapanCount, supportCount))
            }
        }
    }

    fun getListTanggapanItem(size: Int): ArrayList<TanggapanItem> {
        val faker = Faker()
        val data = ArrayList<TanggapanItem>()
        val cal = getCalendarDate()
        return data.apply {
            for (i in 1..size) {
                val id = "$i"
                val name = faker.name.nameWithMiddle()
                val date = cal.time
                val tanggapan = getRandomText(1, 4)
                Log.d("faker_tanggapan", "$id, $name, $date, $tanggapan")
                add(TanggapanItem(id, name, date, tanggapan))
            }
        }
    }

    fun getChat(isUser: Boolean, size: Int): ArrayList<ChatItem> {
        val data = ArrayList<ChatItem>()
        return data.apply {
            for (i in 1..size) {
                val id = "$i"
                val chat = getRandomText(1, 4)
                Log.d("faker_chat", "$id, $chat")
                add(ChatItem(id, isUser, chat))
            }
        }
    }

    fun getListLaporan(size: Int): ArrayList<LaporanItem> {
        val data = ArrayList<LaporanItem>()
        val cal = getCalendarDate()
        return data.apply {
            for (i in 1..size) {
                val id = "$i"
                val date = cal.time
                Log.d("faker_laporan", "$id, $date")
                add(LaporanItem(id, date))
            }
        }
    }

}