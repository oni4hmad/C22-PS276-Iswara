package com.example.iswara.ui.ruang_cerita.detail_tanggapan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iswara.databinding.ItemRowCeritaBinding
import com.example.iswara.databinding.ItemRowTanggapanBinding
import com.example.iswara.ui.ruang_cerita.CeritaItem
import com.example.iswara.ui.ruang_cerita.ListCeritaAdapter
import java.text.SimpleDateFormat

class ListTanggapanAdapter(private val listTanggapan: List<TanggapanItem>) : RecyclerView.Adapter<ListTanggapanAdapter.ListViewHolder>() {

    /*private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowTanggapanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listTanggapan[position])
    }

    override fun getItemCount(): Int = listTanggapan.size

    inner class ListViewHolder(private var binding: ItemRowTanggapanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tanggapan: TanggapanItem) {

//            binding.ivUser.setBackgroundColor(Color.rgb(255, 0, 0))
//            binding.ivUser.setBackgroundResource(R.color.orange)
            Glide.with(binding.ivUser.context)
                .load("https://loremflickr.com/200/100")
                .into(binding.ivUser)

            binding.tvName.text = tanggapan.name
            binding.tvDate.text = tanggapan.date.let {
                val formatter = SimpleDateFormat("d MMMM yyyy")
                formatter.format(it)
            }
            binding.tvTanggapan.text = tanggapan.tanggapan
        }
    }

}