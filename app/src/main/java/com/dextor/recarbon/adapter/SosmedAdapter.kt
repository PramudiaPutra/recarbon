package com.dextor.recarbon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dextor.recarbon.model.SosmedData
import com.dextor.recarbon.databinding.RecyclerSosmedBinding

class SosmedAdapter(
    private val items: ArrayList<SosmedData>
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
        val dataV = items[position]
        holder.bind(dataV, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class SosmedViewHolder(private val binding: RecyclerSosmedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            sosmedData: SosmedData,
            position: Int
        ) = with(binding) {
            imgUserSosmed.setImageResource(sosmedData.imgUser)
            usernameSosmed.text = sosmedData.username.toString()
            locationSosmed.text = sosmedData.location
            dateSosmed.text = sosmedData.date
            imgSosmed.setImageBitmap(sosmedData.imgStory)
            titleSosmed.text = sosmedData.title
            contentSosmed.text = sosmedData.content
        }
    }
}