package com.nemodroid.searchimage.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nemodroid.searchimage.BuildConfig
import com.nemodroid.searchimage.R
import com.nemodroid.searchimage.utils.constant
import com.nemodroid.searchimage.utils.constant.CONNECTION_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {

        private fun getOkHttpClient(context: Context, lang: String): OkHttpClient {

            var builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES)

            var interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            builder.addInterceptor(interceptor)

            builder.addInterceptor {
                var request = it.request().newBuilder()
                request.addHeader("Accept-Version", "v1")
                request.addHeader("Client-ID", BuildConfig.UNSPLASH_ACCESS_KEY)
                return@addInterceptor it.proceed(request.build())
            }

            return builder.build()
        }

        private fun getGSONBuilder(): Gson {
            return GsonBuilder().setLenient().create()
        }

        private var retrofitBuilder = Retrofit.Builder()
            .baseUrl(constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(getGSONBuilder()))

        private fun createService(
            retrofitApi: Class<RetrofitAPI>,
            context: Context,
            lang: String
        ): RetrofitAPI {
            val retrofit = retrofitBuilder.client(getOkHttpClient(context, lang)).build()
            return retrofit.create(retrofitApi)
        }

        fun fetchErrorMessage(context: Context, throwable: Throwable?): String? {
            val errorMsg: String = if (!isNetworkAvailable(context)) {
                context.getString(R.string.noInternetConnection)
            } else if (throwable is IOException) {
                //“Timeout”;
                context.getString(R.string.serverError)
            } else if (throwable is IllegalStateException) {
                //“ConversionError”;
                context.getString(R.string.parseError)
            } else {
                context.getString(R.string.timeOutConnection)
            }
            return errorMsg
        }

        private fun isNetworkAvailable(context: Context?): Boolean {
            if (context == null) return false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            return true
                        }
                    }
                }
            } else {
                try {
                    val activeNetworkInfo = connectivityManager.activeNetworkInfo
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                        Log.i("isNetworkAvailable", "Network is available : true")
                        return true
                    }
                } catch (e: Exception) {
                    Log.i("isNetworkAvailable", "" + e.message)
                }
            }
            return false
        }

    }

}