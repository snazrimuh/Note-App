package com.simple.note.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simple.note.data.Catatan
import com.simple.note.databinding.ItemCatatanBinding

class CatatanAdapter(
    private var catatanList: List<Catatan>,
    private val onDeleteClick: (Catatan) -> Unit,
    private val onUpdateClick: (Catatan) -> Unit
) : RecyclerView.Adapter<CatatanAdapter.CatatanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatatanViewHolder {
        val binding = ItemCatatanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatatanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatatanViewHolder, position: Int) {
        val catatan = catatanList[position]
        holder.bind(catatan)
    }

    override fun getItemCount(): Int = catatanList.size

    fun setCatatan(catatan: List<Catatan>) {
        catatanList = catatan
        notifyDataSetChanged()
    }

    inner class CatatanViewHolder(private val binding: ItemCatatanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(catatan: Catatan) {
            with(binding) {
                textViewJudul.text = catatan.judul
                textViewIsi.text = catatan.isi

                buttonDelete.setOnClickListener { onDeleteClick(catatan) }
                buttonUpdate.setOnClickListener { onUpdateClick(catatan) }
            }
        }
    }
}
