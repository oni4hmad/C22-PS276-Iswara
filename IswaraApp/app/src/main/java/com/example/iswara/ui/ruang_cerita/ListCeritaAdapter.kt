package com.example.iswara.ui.ruang_cerita

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iswara.data.network.Cerita
import com.example.iswara.databinding.ItemRowCeritaBinding
import com.example.iswara.utils.formatDateString

class ListCeritaAdapter(private val listCerita: List<Cerita>) : RecyclerView.Adapter<ListCeritaAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowCeritaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listCerita[position])
    }

    override fun getItemCount(): Int = listCerita.size

    inner class ListViewHolder(private var binding: ItemRowCeritaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cerita: Cerita) {

//            binding.ivUser.setBackgroundColor(Color.rgb(255, 0, 0))
//            binding.ivUser.setBackgroundResource(R.color.orange)
            Glide.with(binding.ivUser.context)
                .load("https://loremflickr.com/200/100")
                .into(binding.ivUser)

            binding.tvName.text = cerita.name

            binding.tvDate.text = formatDateString(cerita.date)

            binding.tvCerita.text = cerita.cerita.let {
                if (it.length > 300) {
                    binding.tvReadMore.visibility = View.VISIBLE
                    it.take(300) + "..."
                } else {
                    binding.tvReadMore.visibility = View.GONE
                    it
                }
            }
            binding.tvTanggapan.text = "${cerita.tanggapanCount} tanggapan"
            binding.tvSupport.text = "${cerita.supportCount} support"

            binding.tvReadMore.setOnClickListener { onItemClickCallback.onItemClicked(cerita) }
            binding.cvItem.setOnClickListener { onItemClickCallback.onItemClicked(cerita) }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(cerita: Cerita)
    }
}