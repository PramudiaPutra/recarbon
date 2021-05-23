package com.dextor.recarbon.features.article

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.dextor.recarbon.R
import com.dextor.recarbon.model.ArticleData
import com.dextor.recarbon.databinding.RecyclerArticleBinding

class ArticleAdapter(
    private val context: Context,
    private val items: List<ArticleData>,
    private val listener: (ArticleData) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerArticleBinding.inflate(inflater, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(items[position], listener)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ArticleViewHolder(private val binding: RecyclerArticleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            articleData: ArticleData, listener: (ArticleData) -> Unit
        ) = with(binding) {

            titleArticle.text = articleData.title
            contentArticle.text = articleData.content
            profileArticle.text = articleData.author
            ageArticle.text = articleData.age
            imageProfileArticle.setImageResource(articleData.authorImage)
            itemView.setOnClickListener { listener(articleData) }
        }
    }

}
