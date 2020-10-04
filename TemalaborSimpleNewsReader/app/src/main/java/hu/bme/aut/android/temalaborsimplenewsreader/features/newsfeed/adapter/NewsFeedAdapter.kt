package hu.bme.aut.android.temalaborsimplenewsreader.features.newsfeed.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.marginRight
import androidx.recyclerview.widget.RecyclerView
import com.codesgood.views.JustifiedTextView
import hu.bme.aut.android.temalaborsimplenewsreader.R
import hu.bme.aut.android.temalaborsimplenewsreader.features.newsfeed.model.Article
import hu.bme.aut.android.temalaborsimplenewsreader.network.HttpUrlConnectionNetworkManager
import hu.bme.aut.android.temalaborsimplenewsreader.network.INetworkManager
import hu.bme.aut.android.temalaborsimplenewsreader.temporary.LoremJsonProcessor
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
    private val articles = mutableListOf<Article>()

    companion object {

        /**
         * This member stores the URL, which can be used to reach the REST API.
         */
        @Suppress("unused") //TODO in the final version, this member will be used
        private const val REST_DATA_URL = ""  //TODO backend is still not complete...

        //TODO: this should be removed, this member is sole purpose to give the ability to use the
        //  the application until the backend becomes available
        private const val TEST_REST_DATA_URL = "https://asdfast.beobit.net/api/"
    }

    /**
     * In this init block, the adapter's data is being set.
     */
    init {
        GlobalScope.launch(Dispatchers.Main) {
            val networkManager: INetworkManager = HttpUrlConnectionNetworkManager
            try {
                //TODO this commented code below will parse and set the data from backend's data
                /**
                val jsonString = networkManager.getHttpAnswer(REST_DATA_URL)
                setArticles(processJsonDataIntoArticles(jsonString))
                */
                //TODO: this temporary solution should be removed later, its sole purpose to test
                //  the application until the backend becomes available
                val longLorem = LoremJsonProcessor.processLoremJsonString(
                    networkManager.getHttpAnswer(TEST_REST_DATA_URL))
                val shortLorem = "Lorem ipsum dolor sit amet"
                setArticles(listOf(Article(
                    shortLorem, listOf("lorem", "ipsum"), shortLorem, shortLorem, longLorem
                )))
            } catch (e: IOException) {
                Log.w("NewsFeedAdapter",
                    e.message ?: "Problem occurred with reading the response!")
                Toast.makeText(
                    activityContext, "Check your internet connection!",
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
    @Suppress("unused") //TODO in the final version this function will be used
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
                //There is bug, which prevents the layout_margin attribute's value from the custom
                // style to be applied according to the bug report below
                //https://github.com/google/flexbox-layout/issues/417
                TextView(ContextThemeWrapper(holder.llTags.context, R.style.TagTextView)).apply {
                    text = concreteTag
                    //because of the bug explained above, this code is needed to fix it
                    val tvTagLayoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                    tvTagLayoutParams.marginEnd = 10
                    tvTagLayoutParams.rightMargin = 10
                    layoutParams = tvTagLayoutParams
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