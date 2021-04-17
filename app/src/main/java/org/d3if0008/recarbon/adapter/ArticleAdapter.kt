package org.d3if0008.recarbon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.d3if0008.recarbon.data.ArticleData
import org.d3if0008.recarbon.databinding.RecyclerArticleBinding

class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<ArticleData> = ArrayList()

    fun addArticle(article: List<ArticleData>) {
        items = article
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerArticleBinding.inflate(inflater, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleViewHolder) {
            holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ArticleViewHolder(private val binding: RecyclerArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articleData: ArticleData) = with(binding) {
            titleArticle.text = articleData.title
            contentArticle.text = articleData.content
            profileArticle.text = articleData.author
            ageArticle.text = articleData.age
            imageProfileArticle.setImageResource(articleData.authorImage)
        }
    }

}