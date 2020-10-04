package hu.bme.aut.android.temalaborsimplenewsreader.features.newsfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.temalaborsimplenewsreader.R
import hu.bme.aut.android.temalaborsimplenewsreader.features.newsfeed.adapter.NewsFeedAdapter
import kotlinx.android.synthetic.main.activity_news_feed.*

/**
 * This Activity is the application's only Activity. It's responsibility to show the user the
 * news feed in a RecyclerView.
 */
class NewsFeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_feed)

        //setting the recyclerview's adapter
        val newsFeedAdapter = NewsFeedAdapter(this)
        rvNewsFeed.layoutManager = LinearLayoutManager(this)
        rvNewsFeed.adapter = newsFeedAdapter
    }
}