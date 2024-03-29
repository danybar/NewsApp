package com.example.newsapp.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.Article

class ArticlesAdapter : ListAdapter<Article, ArticlesAdapter.ArticleViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.articleTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.articleDescription)
        private val imageView: ImageView = itemView.findViewById(R.id.articleImage)
        private val saveButton: Button = itemView.findViewById(R.id.saveArticleButton)

        fun bind(article: Article) {
            titleTextView.text = article.title
            descriptionTextView.text = article.description
            Glide.with(itemView.context).load(article.urlToImage).into(imageView)
            saveButton.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    saveArticleToSharedPreferences(article.title, itemView.context)
                }
            }
        }

        private fun saveArticleToSharedPreferences(articleTitle: String, context: Context) {
            val sharedPrefs = context.getSharedPreferences("MyAppSharedPreferences", Context.MODE_PRIVATE)
            with(sharedPrefs.edit()) {
                putString(articleTitle, articleTitle) // Ukládání titulku článku
                apply()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}