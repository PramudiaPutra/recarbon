package com.dextor.recarbon.features.calculator.categories

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dextor.recarbon.model.CategoryData
import com.dextor.recarbon.databinding.RecyclerCategoryItemBinding
import com.dextor.recarbon.features.calculator.AddCalculateActivity

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
            cvKategori.setCardBackgroundColor(Color.parseColor(categoryData.color))
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: CategoryData)
    }
}