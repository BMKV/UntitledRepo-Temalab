package hu.bme.aut.android.temalaborsimplenewsreader.features.newsfeed.adapter

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
import org.json.JSONObject
import java.io.IOException
import java.net.SocketTimeoutException

class NewsFeedAdapter : RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder>() {

    private val articles = mutableListOf<Article>()

    companion object{
        private const val REST_DATA_URL = ""  //TODO
    }

    init{
        val networkManager: INetworkManager = HttpUrlConnectionNetworkManager
        try {
            //TODO This must be done in a separate thread!
            val jsonData = networkManager.httpGet(REST_DATA_URL)
            val jsonRootObject = JSONObject(jsonData)
            TODO("This code will depend on the backend's concrete implementation")
        }
        catch(e: IOException){
            TODO()
        }
        catch(e: SocketTimeoutException){
            TODO()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_news_feed, null)
        return NewsFeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        val article = articles[position]
        holder.tvTitle.text = article.title
        for(concreteTag in article.tags){
            (LayoutInflater.from(holder.llTags.context).inflate(R.layout.element_tag_news_feed,
                holder.llTags, true) as? TextView).apply{ this?.text = concreteTag }
        }
        holder.tvAuthorName.text = article.author
        holder.tvPublishDate.text = article.date
        holder.jtvArticleContent.text = article.data
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun setArticles(contacts: List<Article>) {
        articles.clear()
        articles += contacts
        notifyDataSetChanged()
    }

    class NewsFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitle = itemView.tvTitle
        val llTags = itemView.llTags
        val tvAuthorName = itemView.tvAuthorName
        val tvPublishDate = itemView.tvPublishDate
        val jtvArticleContent = itemView.jtvArticleContent
    }

}