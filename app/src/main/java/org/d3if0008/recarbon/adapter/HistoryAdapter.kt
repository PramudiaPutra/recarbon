package org.d3if0008.recarbon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.d3if0008.recarbon.data.HistoryData
import org.d3if0008.recarbon.databinding.RecyclerHistoryHeaderBinding
import org.d3if0008.recarbon.databinding.RecyclerHistoryItemBinding

class HistoryAdapter(private val items: ArrayList<HistoryData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerHistoryHeaderBinding.inflate(inflater, parent, false)
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (holder is DateViewHolder) {
            holder.tvDate.text = item.date

        }
        else if(holder is ItemViewHolder){
            holder.icon.setImageResource(item.icon)
            holder.title.text = item.title
            holder.time.text = item.time
            holder.description.text = item.description
            holder.carbon.text = item.carbon
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class DateViewHolder(binding: RecyclerHistoryHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvDate = binding.dateHistory
    }

    class ItemViewHolder(binding: RecyclerHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val icon = binding.iconHistory
        val title = binding.titleHistory
        val time = binding.timeHistory
        val description = binding.descriptionHistory
        val carbon = binding.carbonHistory
    }
}