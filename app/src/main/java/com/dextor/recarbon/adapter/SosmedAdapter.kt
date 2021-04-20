package com.dextor.recarbon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dextor.recarbon.data.SosmedData
import com.dextor.recarbon.databinding.RecyclerSosmedBinding

class SosmedAdapter(
    private val context: Context,
    private val items: List<SosmedData>
) : RecyclerView.Adapter<SosmedAdapter.SosmedViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SosmedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerSosmedBinding.inflate(inflater, parent, false)
        return SosmedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SosmedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SosmedViewHolder(private val binding: RecyclerSosmedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            sosmedData: SosmedData
        ) = with(binding) {
            imgUserSosmed.setImageResource(sosmedData.imgUser)
            usernameSosmed.text = sosmedData.username
            locationSosmed.text = sosmedData.location
            dateSosmed.text = sosmedData.date
            imgSosmed.setImageResource(sosmedData.imgStory)
            titleSosmed.text = sosmedData.title
            contentSosmed.text = sosmedData.content
        }
    }
}