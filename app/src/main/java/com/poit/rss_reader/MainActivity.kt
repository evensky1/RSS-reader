package com.poit.rss_reader

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poit.rss_reader.adapter.ItemClickInterface
import com.poit.rss_reader.adapter.NewsRVAdapter
import com.poit.rss_reader.model.Item
import com.poit.rss_reader.repository.RssFeedFetcher
import com.poit.rss_reader.viewmodel.RssViewModel

class MainActivity : AppCompatActivity(), ItemClickInterface {
    private lateinit var newsFeed: RecyclerView
    private lateinit var searchEdit: EditText
    private lateinit var getRssButton: Button
    private lateinit var viewModel: RssViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsFeed = findViewById(R.id.rvNews)
        newsFeed.layoutManager = LinearLayoutManager(this)
        searchEdit = findViewById(R.id.findByTitleEdit)
        getRssButton = findViewById(R.id.getRss)
        val adapter = NewsRVAdapter(this, this)
        newsFeed.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[RssViewModel::class.java]

        viewModel.feed.observe(this) { rss ->
            rss?.let { adapter.updateList(it.channel.items) }
        }

        getRssButton.setOnClickListener {
            viewModel.updateFeed(searchEdit.text.toString())
        }
    }

    override fun onItemClick(item: Item) {
        TODO("Not yet implemented")
    }
}