package hu.bme.aut.android.temalaborsimplenewsreader.network

/**
 * INetworkManager's responsibility to provide an interface to use network communication.
 */
interface INetworkManager {

    /**
     * This function's implementations sends a http get request to the specified address,
     * and returns with the response's body. This function is a suspend function, because
     * the implementations should use network communication to send the get request, and
     * get the response.
     * @param urlAddress the address of the http get message.
     * @return the body of the http response.
     */
    suspend fun getHttpAnswer(urlAddress: String): String
}