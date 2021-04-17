package org.d3if0008.recarbon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.d3if0008.recarbon.data.ArticleData
import org.d3if0008.recarbon.databinding.ActivityArticleBinding
import org.d3if0008.recarbon.fragment.ArticleFragment

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