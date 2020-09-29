package hu.bme.aut.android.temalaborsimplenewsreader.network

interface INetworkManager {

    fun httpGet(urlAddress: String): String
}