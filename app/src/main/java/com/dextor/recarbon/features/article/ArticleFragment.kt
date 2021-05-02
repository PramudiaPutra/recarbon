package com.dextor.recarbon.features.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dextor.recarbon.databinding.FragmentArticleBinding
import com.dextor.recarbon.data.ArticleDummy

class ArticleFragment : Fragment() {

    companion object{
        const val INTENT_ITEM = "OBJECT_INTENT"
    }

    private lateinit var binding: FragmentArticleBinding
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(layoutInflater, container, false)

        initRecycler()
        return binding.root
    }

    private fun initRecycler() {
        val getArticle = ArticleDummy.dataSet()
        binding.rvArticleList.apply {
            layoutManager = LinearLayoutManager(context)
            articleAdapter = ArticleAdapter(context,getArticle) {
                val intent = Intent(context, ArticleActivity::class.java)
                intent.putExtra(INTENT_ITEM,it)
                startActivity(intent)
            }
            adapter = articleAdapter
        }
    }

}