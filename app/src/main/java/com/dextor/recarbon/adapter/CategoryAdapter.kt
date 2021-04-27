package com.dextor.recarbon.adapter

import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.dextor.recarbon.AddCalculateActivity
import com.dextor.recarbon.R
import com.dextor.recarbon.data.CategoryData
import com.dextor.recarbon.databinding.ActivityAddCalculateBinding
import com.dextor.recarbon.databinding.RecyclerCategoryItemBinding
import com.dextor.recarbon.fragment.HomeFragment
import com.dextor.recarbon.fragment.MobilDetailFragment
import javax.security.auth.callback.Callback

class CategoryAdapter(
    items1: AddCalculateActivity,
    private val items: List<CategoryData>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerCategoryItemBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(items[holder.adapterPosition])
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class CategoryViewHolder(private val binding: RecyclerCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            categoryData: CategoryData
        ) = with(binding) {
            imgKategori.setImageResource(categoryData.categoryImage)
            tvJudulKategori.text = categoryData.categoryTitle
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: CategoryData)
    }

//    private fun setFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
//        replace(R.id.rv_kategori,fragment)
//        commit()
//    }
}