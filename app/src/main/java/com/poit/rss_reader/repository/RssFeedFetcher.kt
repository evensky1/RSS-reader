package com.poit.rss_reader.repository

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.poit.rss_reader.model.RSS
import com.poit.rss_reader.model.RssItem
import okhttp3.*
import org.simpleframework.xml.core.Persister
import java.io.IOException

class RssFeedFetcher {
    val rssItemsCache = mutableMapOf(
        "https://soundcloud.com/armonxx" to "http://fetchrss.com/rss/63f0b4cf050aa0595839ca9263f0c6a64adcee776a404952.xml",
        "https://www.youtube.com/channel/UCI1p0_8jEGM9yabxPib5WzQ" to "http://fetchrss.com/rss/63f0b4cf050aa0595839ca9263f0c4f960da5d61283b1772.xml",
        "https://www.youtube.com/channel/UC6nSFpj9HTCZ5t-N3Rm3-HA" to "http://fetchrss.com/rss/63f0b4cf050aa0595839ca9263f0b8b247eaca0553468042.xml"
    )

    val rssFeed = MutableLiveData<RSS>()
    private val client = OkHttpClient()

    companion object {
        private const val AUTH_KEY = ""
        private fun createUri(srcUrl: String): String {
            return Uri.Builder()
                .scheme("https")
                .authority("fetchrss.com")
                .appendPath("api")
                .appendPath("v1")
                .appendPath("feed")
                .appendPath("create")
                .appendQueryParameter("auth", AUTH_KEY)
                .appendQueryParameter("url", srcUrl)
                .build()
                .toString()
        }
    }

    fun loadRssItem(srcUrl: String) {
        if (srcUrl == "") return

        rssItemsCache[srcUrl]?.let {
            loadRssXml(it)
            return
        }

        val req = Request.Builder()
            .url(createUri(srcUrl))
            .build()

        client.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val rssItem = Gson().fromJson(response.body?.string(), RssItem::class.java)
                    if (rssItem.success) {
                        val rssUrl = rssItem.feed?.rssUrl ?: ""
                        loadRssXml(rssUrl)
                        rssItemsCache[srcUrl] = rssUrl
                    }
                }
            }
        })
    }

    private fun loadRssXml(rssUrl: String) {
        val req = Request.Builder()
            .url(rssUrl)
            .build()

        client.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val serializer = Persister()
                    val xmlStr =
                        it.body!!.string().replace("<atom:link[\\s\\S]*?\\/>".toRegex(), "")
                    val dataFetch = serializer.read(RSS::class.java, xmlStr)
                    rssFeed.postValue(dataFetch)
                }
            }
        })
    }
}