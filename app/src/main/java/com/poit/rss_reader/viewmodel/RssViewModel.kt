package com.poit.rss_reader.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.poit.rss_reader.model.RSS
import com.poit.rss_reader.repository.RssFeedFetcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RssViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RssFeedFetcher = RssFeedFetcher()
    val feed: MutableLiveData<RSS> = repository.rssFeed

    fun updateFeed(url: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.loadRssItem(url)
    }
}