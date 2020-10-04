package hu.bme.aut.android.temalaborsimplenewsreader.temporary

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

object LoremJsonProcessor {

    suspend fun processLoremJsonString(jsonString : String): String = withContext(Dispatchers.Default){
        return@withContext JSONObject(jsonString).getString("text")
    }

}