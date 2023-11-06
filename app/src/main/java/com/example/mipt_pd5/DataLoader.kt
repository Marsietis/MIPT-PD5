package com.example.mipt_pd5

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

private const val ECB_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml"

class DataLoader {

    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    @Throws(IOException::class)
    private fun downloadUrl(): InputStream? {
        Log.d("DataLoader", "downloadUrl")
        val url = URL(ECB_URL)
        return (url.openConnection() as? HttpURLConnection)?.run {
            readTimeout = 10000
            connectTimeout = 15000
            requestMethod = "GET"
            doInput = true
            // Starts the query.
            connect()
            inputStream
        }
    }

    suspend fun loadData(): ArrayList<HashMap<String, String>> {
        Log.d("DataLoader", "loadData")
        return withContext(Dispatchers.IO) {
            val inputStream = downloadUrl()
            if (inputStream != null) {
                val parser = Parser()
                parser.parseXml(inputStream)
            } else {
                // Handle the case where inputStream is null and return an empty list
                ArrayList()
            }
        }
    }
}