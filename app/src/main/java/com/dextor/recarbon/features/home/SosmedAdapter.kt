package com.dextor.recarbon.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dextor.recarbon.R
import com.dextor.recarbon.model.SosmedData
import com.dextor.recarbon.databinding.RecyclerSosmedBinding

class SosmedAdapter(private val items: ArrayList<SosmedData>) :
    RecyclerView.Adapter<SosmedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerSosmedBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataV = items[position]
        holder.bind(dataV, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(private val binding: RecyclerSosmedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sosmedData: SosmedData, position: Int) = with(binding) {

            imgUserSosmed.setImageResource(sosmedData.imgUser!!)
            usernameSosmed.text = sosmedData.username.toString()
            locationSosmed.text = sosmedData.location
            dateSosmed.text = sosmedData.date
            titleSosmed.text = sosmedData.title
            contentSosmed.text = sosmedData.content

            //Get Image
            val requestOption = RequestOptions()
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_failed)

            Glide.with(imgSosmed.context)
                .applyDefaultRequestOptions(requestOption)
                .load(sosmedData.imgStory)
                .into(imgSosmed)
        }
    }
}