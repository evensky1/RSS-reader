package com.poit.rss_reader.repository

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.graphics.get
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.poit.rss_reader.model.RSS
import com.poit.rss_reader.model.RssItem
import okhttp3.*
import org.simpleframework.xml.core.Persister
import java.io.IOException
import java.net.URL

class RssFeedFetcher {
    val rssItemsCache = mutableMapOf<String, RssItem>()
    val rssFeed = MutableLiveData<RSS>()
    private val client = OkHttpClient()

    companion object {
        private const val AUTH_KEY = "63f0b4cf050aa0595839ca92.J0e9s5zA4KzefZb8o8"
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
        //loadRssXml("http://fetchrss.com/rss/63f0b4cf050aa0595839ca9263f0c6a64adcee776a404952.xml")
        loadRssXml("http://fetchrss.com/rss/63f0b4cf050aa0595839ca9263f0b8b247eaca0553468042.xml")
//        if (srcUrl in rssItemsCache) {
//            loadRssXml(rssItemsCache[srcUrl]?.feed?.rssUrl ?: "")
//            return
//        }
//
//        val req = Request.Builder()
//            .url(createUri(srcUrl))
//            .build()
//
//        client.newCall(req).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                response.use {
//                    val rssItem = Gson().fromJson(response.body?.string(), RssItem::class.java)
//                    if (rssItem.success) {
//                        loadRssXml(rssItem.feed?.rssUrl ?: "")
//                        rssItemsCache.put(srcUrl, rssItem)
//                    }
//                }
//            }
//        })
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
                    val xmlStr = it.body!!.string().replace("<atom:link[\\s\\S]*?\\/>".toRegex(), "")
                    val dataFetch = serializer.read(RSS::class.java, xmlStr)
                    dataFetch.channel.items.forEach {item ->
                        val url = URL(item.content.url)
                        val connection = url.openConnection()
                        item.bmp = BitmapFactory.decodeStream(connection.getInputStream())

                        println(item.bmp?.get(1, 1))
                        println(item.bmp?.get(10, 10))
                        println(item.bmp?.get(100, 100))
                        println(item.bmp?.get(40, 50))
                        println(item.bmp?.get(12, 35))
                    }
                    rssFeed.postValue(dataFetch)
                }
            }
        })
    }
}