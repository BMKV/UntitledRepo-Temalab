package hu.bme.aut.android.temalaborsimplenewsreader.features.newsfeed.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.codesgood.views.JustifiedTextView
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
import java.util.*

/**
 * This class's responsibility is to implement the NewsFeedActivity's RecyclerView's behaviour, and
 * obviously make it able to this class's instances to be that RecyclerView's adapter.
 */
class NewsFeedAdapter(private val activityContext: Context) :
    RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder>() {

    /**
     * This variable stores the reference of Article instances, that contain the data of the
     * articles.
     */
    private val articles = mutableListOf<Article>() //TODO this needs to be set

    companion object {

        /**
         * This member stores the URL, which can be used to reach the REST API.
         */
        private const val REST_DATA_URL = ""  //TODO backend is still not complete...
    }

    /**
     * In this init block, the adapter's data is being set.
     */
    init {
        GlobalScope.launch(Dispatchers.Main) {
            val networkManager: INetworkManager = HttpUrlConnectionNetworkManager
            try {
                val jsonString = networkManager.getHttpAnswer(REST_DATA_URL)
                setArticles(processJsonDataIntoArticles(jsonString))
                TODO("Here the List containing the Articles will be set")
            } catch (e: IOException) {
                Log.w("NewsFeedAdapter", "Problem occurred with reading the response!")
                Toast.makeText(
                    activityContext, "Unexpected problem occurred!",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: SocketTimeoutException) {
                Log.i("NewsFeedAdapter", e.message ?: "Connection timed out!")
                Toast.makeText(
                    activityContext, "Check your connection!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //TODO this function is incomplete because there has been no usable backend available yet
    /**
     * This function processes the given JSON String, and converts it into a List of Article instances.
     */
    private suspend fun processJsonDataIntoArticles(jsonString: String) =
        withContext(Dispatchers.Default) {
            val jsonRootObject = JSONObject(jsonString)
            val listOfArticles = mutableListOf<Article>()
            //TODO articles could be not right below
            val jsonArticles = jsonRootObject.getJSONArray("articles")
            for (i in 0 until jsonArticles.length()) {
                val jsonArticle = jsonArticles.getJSONObject(i)
                listOfArticles.add(
                    Article(
                        //TODO the name of the attributes could be different from this
                        jsonArticle.getString("title"),
                        jsonArticle.getString("tags").split("#"),
                        jsonArticle.getString("date"),
                        jsonArticle.getString("author"),
                        jsonArticle.getString("data")
                    )
                )
            }
            return@withContext Collections.unmodifiableList(listOfArticles)
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
                TextView(ContextThemeWrapper(holder.llTags.context, R.style.TagTextView)).apply {
                    text = concreteTag
                }
            )
        }
        holder.apply {
            tvTitle.text = article.title
            tvAuthorName.text = article.author
            tvPublishDate.text = article.date
            jtvArticleContent.text = article.data
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    /**
     * This function clears the stored data of Article instances, and replaces it with the given
     * parameter's Article instances.
     * @param newArticles a List of Article instances, of which data will be set to be adapter's
     * new contained data.
     */
    private fun setArticles(newArticles: List<Article>) {
        articles.clear()
        articles += newArticles
        notifyDataSetChanged()
    }

    class NewsFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.tvTitle
        val llTags: LinearLayout = itemView.llTags
        val tvAuthorName: TextView = itemView.tvAuthorName
        val tvPublishDate: TextView = itemView.tvPublishDate
        val jtvArticleContent: JustifiedTextView = itemView.jtvArticleContent
    }

}