package hu.bme.aut.android.temalaborsimplenewsreader.features.newsfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.temalaborsimplenewsreader.R
import hu.bme.aut.android.temalaborsimplenewsreader.features.newsfeed.adapter.NewsFeedAdapter
import kotlinx.android.synthetic.main.activity_news_feed.*

class NewsFeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_feed)

        //setting the recyclerview's adapter
        val newsFeedAdapter = NewsFeedAdapter()
        rvNewsFeed.layoutManager = LinearLayoutManager(this)
        rvNewsFeed.adapter = newsFeedAdapter
    }
}