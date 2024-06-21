package com.example.florascan.ui.news

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.florascan.R
import com.example.florascan.databinding.ItemNewsBinding
import com.example.florascan.ui.news.entity.NewsEntity
import com.example.florascan.utils.DateFormatter

class NewsAdapter(private val onBookmarkClick: (NewsEntity) -> Unit) :
    ListAdapter<NewsEntity, NewsAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)

        val ivBookmark = holder.binding.ivBookmark
        if (news.isBookmarked) {
            ivBookmark.setImageDrawable(
                ContextCompat.getDrawable(
                    ivBookmark.context,
                    R.drawable.ic_bookmarked_white
                )
            )
        } else {
            ivBookmark.setImageDrawable(
                ContextCompat.getDrawable(
                    ivBookmark.context,
                    R.drawable.ic_bookmark_white
                )
            )
        }
        ivBookmark.setOnClickListener {
            onBookmarkClick(news)
        }
    }

    class MyViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsEntity) {
            binding.tvItemTitle.text = news.title
            binding.tvItemPublishedDate.text = DateFormatter.formatDate(news.publishedAt)
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
                )
                .into(binding.imgPoster)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.EXTRA_URL, news.url)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>() {
                override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
