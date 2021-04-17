package com.dextor.recarbon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dextor.recarbon.data.ArticleData
import com.dextor.recarbon.databinding.ActivityArticleBinding
import com.dextor.recarbon.fragment.ArticleFragment

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent.getParcelableExtra<ArticleData>(ArticleFragment.INTENT_ITEM)
        binding.tvTitle.text = intent?.title
        binding.tvContent.text = intent?.content

        binding.btnBack.setOnClickListener { onBackPressed() }
        binding.tvBack.setOnClickListener { onBackPressed() }
    }
}