package com.poit.rss_reader.model

import org.simpleframework.xml.*

@Root(name = "rss")
data class RSS(
    @field:Element(name = "channel")
    var channel: Channel,

    @field:Attribute(name = "xmlns:media", required = false)
    var xmlnsMedia: String,

    @field:Attribute(name = "xmlns:atom", required = false)
    var xmlnsAtom: String,

    @field:Attribute(name = "xmlns:dc", required = false)
    var xmlnsDc: String,

    @field:Attribute(name = "version", required = false)
    var version: String
) {
    constructor() : this(Channel(), "", "", "", "")
}

@Root(name = "channel")
@Namespace(prefix = "atom", reference = "emt")
data class Channel(
    @field:Element(name = "title", required = false)
    var title: String,
    @field:Element(name = "description", required = false)
    var description: String,
    @field:Element(name = "pubDate", required = false)
    var pubDate: String,
    @field:Element(name = "link", required = false)
    var link: String,
    @field:Element(name = "generator", required = false)
    var generator: String,
    @field:Element(name = "image", type = Image::class)
    var image: Image,
    @field:ElementList(inline = true, name = "item", required = false)
    var items: MutableList<Item>
) {
    constructor() : this("", "", "", "", "", Image(), mutableListOf())
}

@Root(name = "image")
class Image {
    @field:Element(name = "link", required = false)
    var link: String = ""

    @field:Element(name = "url", required = false)
    var url: String = ""

    @field:Element(name = "title", required = false)
    var title: String = ""
}

@Root(name = "item")
data class Item(
    @field:Element(name = "title", required = false)
    var title: String,
    @field:Element(name = "description", required = false)
    var description: String,
    @field:Element(name = "link", required = false)
    var link: String,
    @field:Element(name = "creator", required = false)
    var creator: String,
    @field:Element(name = "pubDate", required = false)
    var pubDate: String,
    @field:Element(name = "content", required = false)
    var content: Content,
    @field:Element(name = "guid", required = false)
    var guid: String
) {
    constructor() : this("", "", "", "", "", Content(), "")
}

@Root(name = "content")
class Content(
    @field:Attribute(name = "url")
    var url: String,
    @field:Attribute(name = "medium")
    var medium: String,
    @field:Attribute(name = "width", required = false)
    var width: String,
    @field:Attribute(name = "height", required = false)
    var height: String
) {
    constructor() : this("", "", "", "")
}
