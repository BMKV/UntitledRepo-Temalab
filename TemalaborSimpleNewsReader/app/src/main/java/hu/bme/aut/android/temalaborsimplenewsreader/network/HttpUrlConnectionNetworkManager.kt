package hu.bme.aut.android.temalaborsimplenewsreader.network

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object HttpUrlConnectionNetworkManager : INetworkManager {

    private const val connectTimeoutValue = 3000
    private const val readTimeoutValue = 3000

    override fun httpGet(urlAddress: String): String {
        var reader : BufferedReader? = null
        val wholeMessageBuilder = StringBuilder()
        try{
            val url = URL(urlAddress)
            val connection = url.openConnection() as HttpURLConnection
            //in case of any of the timeout scenarios happen SocketTimeoutException will be thrown
            connection.connectTimeout = connectTimeoutValue
            connection.readTimeout = readTimeoutValue
            reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?
            do{
                line = reader.readLine()
                wholeMessageBuilder.appendLine(line)
            } while(line != null)
        }
        catch(e: IOException){
            throw e
        }
        finally{
            if(reader != null)
                try{
                    reader.close()
                }
                catch (e: IOException){
                    throw e
                }
        }
        return wholeMessageBuilder.toString()
    }
}