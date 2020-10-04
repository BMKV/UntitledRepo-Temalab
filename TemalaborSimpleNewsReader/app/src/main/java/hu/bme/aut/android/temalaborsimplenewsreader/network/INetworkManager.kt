package hu.bme.aut.android.temalaborsimplenewsreader.network

interface INetworkManager {

    suspend fun getHttpAnswer(urlAddress: String): String
}