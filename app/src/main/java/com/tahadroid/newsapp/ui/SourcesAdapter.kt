package com.tahadroid.newsapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tahadroid.newsapp.R
import com.tahadroid.newsapp.pojo.headlines.ArticlesItem
import kotlinx.android.synthetic.main.layout_news_item.view.*
import java.util.ArrayList

class SourcesAdapter(val listener: (View, ArticlesItem, Int) -> Unit)
    :RecyclerView.Adapter<SourcesAdapter.SourcesViewHolder>(){
    private var data: List<ArticlesItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesViewHolder {
        return SourcesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_news_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SourcesViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<ArticlesItem>) {
        this.data = data
        notifyDataSetChanged()
    }
    inner class SourcesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ArticlesItem) = with(itemView) {
            titleTextView.text = item.title
            authorTextView.text = item.author
            Glide.with(itemView).load(item.urlToImage)
                .placeholder(R.drawable.loadimage)
                .error(R.drawable.noimage)
                .into(newsImage)
            setOnClickListener {
                listener.invoke(it, item, adapterPosition)
            }
        }
    }
}