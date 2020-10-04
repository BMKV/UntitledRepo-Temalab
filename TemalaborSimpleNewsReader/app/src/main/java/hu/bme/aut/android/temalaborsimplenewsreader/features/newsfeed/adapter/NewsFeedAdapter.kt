package hu.bme.aut.android.temalaborsimplenewsreader.features.newsfeed.adapter

import android.annotation.SuppressLint
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.temalaborsimplenewsreader.R
import hu.bme.aut.android.temalaborsimplenewsreader.features.newsfeed.model.Article
import hu.bme.aut.android.temalaborsimplenewsreader.network.HttpUrlConnectionNetworkManager
import hu.bme.aut.android.temalaborsimplenewsreader.network.INetworkManager
import kotlinx.android.synthetic.main.element_news_feed.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.net.SocketTimeoutException

class NewsFeedAdapter : RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder>() {

    private val articles = mutableListOf<Article>() //TODO this needs to be set

    companion object {
        private const val REST_DATA_URL = ""  //TODO backend is still not complete...
    }

    init {
        GlobalScope.launch(Dispatchers.Main) {
            val networkManager: INetworkManager = HttpUrlConnectionNetworkManager
            try {
                val jsonString = networkManager.getHttpAnswer(REST_DATA_URL)
                processJsonData(jsonString)

            } catch (e: IOException) {
                TODO()
            } catch (e: SocketTimeoutException) {
                TODO()
            }
        }
    }

    private suspend fun processJsonData(jsonString: String) = withContext(Dispatchers.Default){
        val jsonRootObject = JSONObject(jsonString)
        return@withContext String()
        TODO("This code will depend on the backend's concrete implementation")
    }

    //parent attribute is not used, so setting it null should cause no problems
    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_news_feed, null)
        return NewsFeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        val article = articles[position]
        for (concreteTag in article.tags) {
            holder.llTags.addView(
                TextView(ContextThemeWrapper(holder.llTags.context, R.style.TagTextView)).apply{
                    text = concreteTag
                }
            )
        }
        holder.apply{
            tvTitle.text = article.title
            tvAuthorName.text = article.author
            tvPublishDate.text = article.date
            jtvArticleContent.text = article.data
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun setArticles(contacts: List<Article>) {
        articles.clear()
        articles += contacts
        notifyDataSetChanged()
    }

    class NewsFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.tvTitle
        val llTags = itemView.llTags
        val tvAuthorName = itemView.tvAuthorName
        val tvPublishDate = itemView.tvPublishDate
        val jtvArticleContent = itemView.jtvArticleContent
    }

}