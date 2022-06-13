package com.example.iswara.ui.settings_laporanku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iswara.data.database.Report
import com.example.iswara.databinding.ItemMyLaporanBinding
import com.example.iswara.utils.formatDateString
import java.text.SimpleDateFormat

class LaporanAdapter(private val listLaporan: List<Report>) : RecyclerView.Adapter<LaporanAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemMyLaporanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listLaporan[position])
    }

    override fun getItemCount(): Int = listLaporan.size

    inner class ListViewHolder(private var binding: ItemMyLaporanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(laporan: Report) {

            binding.tvDayName.text = formatDateString(laporan.date)
            binding.clLaporan.setOnClickListener { onItemClickCallback.onItemClicked(laporan) }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(laporan: Report)
    }

}