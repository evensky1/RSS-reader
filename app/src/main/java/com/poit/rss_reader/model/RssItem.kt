package com.poit.rss_reader.model

import com.google.gson.annotations.SerializedName

data class RssItem(
    @SerializedName("success") var success: Boolean,
    @SerializedName("feed") var feed: Feed? = Feed("", ""),
    @SerializedName("target_url") var targetUrl: String? = null
) {
    override fun toString(): String {
        return "RssItem(success=$success, feed=$feed, targetUrl=$targetUrl)"
    }
}

data class Feed(
    @SerializedName("id") var id: String,
    @SerializedName("rss_url") var rssUrl: String
) {
    override fun toString(): String {
        return "Feed(id=$id, rssUrl=$rssUrl)"
    }
}