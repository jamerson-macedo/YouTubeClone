package com.example.youtubeclone

data class Data(
    val duration: Int,
    val id: String,
    val publishedAt: String,
    val publisher: Publisher,
    val thumbnailUrl: String,
    val title: String,
    val videoUrl: String,
    val viewsCount: Int,
    val viewsCountLabel: String
)

data class Publisher(
    val id: String,
    val name: String,
    val pictureProfileUrl: String
)

class PublisherBuilder {
    var id: String = ""
    var name: String = ""
    var pictureProfileUrl: String = ""
    fun builder() = Publisher(id, name, pictureProfileUrl)
}

class VideoBuilder {
    var duration: Int = 0
    var id: String = ""
    var publishedAt: String = ""
    var publisher: Publisher = PublisherBuilder().builder()
    var thumbnailUrl: String = ""
    var title: String = ""
    var videoUrl: String = ""
    var viewsCount: Int = 0
    var viewsCountLabel: String = ""
    fun build()=Data(duration, id, publishedAt, publisher, thumbnailUrl, title, videoUrl, viewsCount, viewsCountLabel)
    fun publisher(block:PublisherBuilder.()-> Unit):Publisher=
        PublisherBuilder().apply(block).builder()
}
//DSL
fun video(block:VideoBuilder.()-> Unit):Data=
    VideoBuilder().apply(block).build()
fun videos():List<Data>{
    return arrayListOf(
        video {
            id="regergergerg"
            thumbnailUrl="https://img.youtube.com/vi/UVpKBHO2fMg/maxresdefault.jpg"
                title="entrevista com Jamerson"
            publishedAt="2019-08-15"
            viewsCount=742497
            duration=1800
            publisher{
                id="sbt"
                name="the noite com danillo"
                pictureProfileUrl="https://yt3.ggpht.com/a/AGF-l7_3BYlSlp94WOjGe1UECUCdb73qRJVFH_t9Tw=s48-c-k-c0xffffffff-no-rj-mo"
            }
        }
    )
}


data class ListVideo(val status: Int, val data: List<Data>)