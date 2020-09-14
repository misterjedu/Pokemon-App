package com.misterjedu.pokemonapp.helpers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class InternetConnection {
    companion object {
        private fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            return activeNetworkInfo != null
        }

        fun hasInternetAccess(context: Context?): Boolean {
            if (isNetworkAvailable(context!!)) {
                var isConnected = false

                GlobalScope.launch(context = Dispatchers.IO) {
                    var connectResponse = false
                    try {
                        val urlc: HttpURLConnection = URL("https://google.com")
                            .openConnection() as HttpURLConnection
                        urlc.setRequestProperty("User-Agent", "Android")
                        urlc.setRequestProperty("Connection", "close")
                        urlc.connectTimeout = 1500
                        urlc.connect()

                        connectResponse = urlc.responseCode == 204 &&
                                urlc.contentLength == 0
                    } catch (e: IOException) {
                        Log.e("Internet Connection", "Error checking internet connection")
                        isConnected = false
                    }
                    launch(context = Dispatchers.Main) {
                        isConnected = connectResponse
                    }

                }
                return isConnected

            } else {
                Log.d("Internet Connection", "No network available!")
                return false
            }
        }
    }


}