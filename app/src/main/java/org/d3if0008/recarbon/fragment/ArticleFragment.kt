package org.d3if0008.recarbon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if0008.recarbon.adapter.ArticleAdapter
import org.d3if0008.recarbon.databinding.FragmentArticleBinding
import org.d3if0008.recarbon.dummy.ArticleDummy

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(layoutInflater, container, false)

        initRecycler()
        insertData()
        return binding.root
    }

    private fun initRecycler() {
        binding.rvArticleList.apply {
            layoutManager = LinearLayoutManager(context)
            articleAdapter = ArticleAdapter()
            adapter = articleAdapter
        }
    }

    private fun insertData() {
        val data = ArticleDummy.dataSet()
        articleAdapter.addArticle(data)
    }
}