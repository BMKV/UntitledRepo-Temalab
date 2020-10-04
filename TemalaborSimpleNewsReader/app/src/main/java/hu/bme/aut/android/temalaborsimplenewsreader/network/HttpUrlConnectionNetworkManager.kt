package hu.bme.aut.android.temalaborsimplenewsreader.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.SocketTimeoutException

/**
 * This object implements the INetworkManager interface with using HttpUrlConnection library.
 */
object HttpUrlConnectionNetworkManager : INetworkManager {

    /**
     * This member holds in milliseconds the connection timeout threshold. If it expires, the
     * connection will throw a SocketTimeoutException.
     */
    private const val connectTimeoutValue = 3000

    /**
     * When reading from the returned input stream, if the read timeout, which is held by this
     * member, expires before data is available for read, the HttpUrlConnection should throw a
     * SocketTimeoutException.
     */
    private const val readTimeoutValue = 3000

    /**
     * With this function, http get requests can be sent to the specified address. The functionality
     * is implemented by using HttpUrlConnection library.
     * @param urlAddress the get request's address.
     * @return the response's body.
     * @throws SocketTimeoutException if the HttpUrlConnection instance's connect timeout or
     * read timeout expires.
     * @throws IOException if some problem happens with reading the InputStream, which contains
     * the response's body.
     */
    //Using Dispatchers.IO should solve this problem
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getHttpAnswer(urlAddress: String) = withContext(Dispatchers.IO) {
        var reader: BufferedReader? = null
        val wholeMessageBuilder = StringBuilder()
        try {
            val url = URL(urlAddress)
            val connection = url.openConnection() as HttpURLConnection
            //in case of any of the timeout scenarios happen SocketTimeoutException will be thrown
            connection.connectTimeout = connectTimeoutValue
            connection.readTimeout = readTimeoutValue
            reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?
            do {
                line = reader.readLine()
                wholeMessageBuilder.appendLine(line)
            } while (line != null)
        } catch (e: IOException) {
            throw e
        } finally {
            if (reader != null)
                try {
                    reader.close()
                } catch (e: IOException) {
                    throw e
                }
        }
        return@withContext wholeMessageBuilder.toString()
    }
}