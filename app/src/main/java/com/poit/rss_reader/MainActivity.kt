package com.poit.rss_reader

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poit.rss_reader.adapter.ItemClickInterface
import com.poit.rss_reader.adapter.NewsRVAdapter
import com.poit.rss_reader.model.Item
import com.poit.rss_reader.viewmodel.RssViewModel

class MainActivity : AppCompatActivity(), ItemClickInterface {
    private lateinit var newsFeed: RecyclerView
    private lateinit var searchEdit: EditText
    private lateinit var getRssButton: Button
    private lateinit var viewModel: RssViewModel
    private lateinit var currentLocation: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsFeed = findViewById(R.id.rvNews)
        newsFeed.layoutManager = LinearLayoutManager(this)
        searchEdit = findViewById(R.id.findByTitleEdit)
        getRssButton = findViewById(R.id.getRss)
        currentLocation = intent.getStringExtra("currLoc") ?: ""
        searchEdit.setText(
            currentLocation,
            TextView.BufferType.EDITABLE
        )
        val adapter = NewsRVAdapter(this, this)
        newsFeed.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[RssViewModel::class.java]

        viewModel.feed.observe(this) { rss ->
            rss?.let { adapter.updateList(it.channel.items) }
        }

        viewModel.updateFeed(currentLocation)

        getRssButton.setOnClickListener {
            viewModel.updateFeed(searchEdit.text.toString())
            currentLocation = searchEdit.text.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("currentLocation", currentLocation)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        searchEdit.setText(
            savedInstanceState.getString("currentLocation"),
            TextView.BufferType.EDITABLE
        )
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onItemClick(item: Item) {
        val intent = Intent(this@MainActivity, WebViewActivity::class.java)
        intent.putExtra("url", item.guid)
        intent.putExtra("currLoc", currentLocation)
        startActivity(intent)
        this.onPause()
    }
}