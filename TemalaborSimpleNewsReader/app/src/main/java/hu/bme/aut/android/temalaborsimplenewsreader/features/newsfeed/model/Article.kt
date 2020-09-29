package hu.bme.aut.android.temalaborsimplenewsreader.features.newsfeed.model

data class Article(
    val title: String,
    val tags: MutableList<String>,
    val date: String,
    val author: String,
    val data: String
)