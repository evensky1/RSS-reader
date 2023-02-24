package com.poit.rss_reader.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.poit.rss_reader.R
import com.poit.rss_reader.model.Item
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.URL
import java.nio.charset.Charset


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
        val image = itemView.findViewById<ImageView>(R.id.itemImage)
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
        println(feed[position].content.url)
        println(feed[position].bmp?.width)
        println(feed[position].bmp?.height)
        println(feed[position].bmp?.rowBytes)
        holder.image.setImageBitmap(
            feed[position].bmp?.let { Bitmap.createScaledBitmap(it, 481, 361, true) })

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
