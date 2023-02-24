package com.poit.rss_reader.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.poit.rss_reader.R
import com.poit.rss_reader.model.Item

class NewsRVAdapter(
    val context: Context,
    private val itemClickInterface: ItemClickInterface
) : RecyclerView.Adapter<NewsRVAdapter.ViewHolder>() {

    private val feed = ArrayList<Item>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.itemTitle)
        val description = itemView.findViewById<TextView>(R.id.itemDescription)
        val pubDate = itemView.findViewById<TextView>(R.id.itemPubDate)
        val author = itemView.findViewById<TextView>(R.id.itemAuthor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.feed_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return feed.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = feed[position].title
        holder.description.text = feed[position].title
        holder.pubDate.text = feed[position].pubDate
        holder.author.text = feed[position].creator

        holder.itemView.setOnClickListener {
            itemClickInterface.onItemClick(feed[position])
        }
    }

    fun updateList(newList: List<Item>) {
        feed.clear()
        feed.addAll(newList)
        notifyDataSetChanged()
    }
}

interface ItemClickInterface {
    fun onItemClick(item: Item)
}
