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
import com.nemodroid.searchimage.utils.Constant
import com.nemodroid.searchimage.utils.NoInternetException
import com.nemodroid.searchimage.utils.prefs.SharedPreferenceUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesJson(): Gson = GsonBuilder()
        .setLenient()
        .create()


    @Provides
    @Singleton
    fun providesOkHttpClient(
        @ApplicationContext application: Context): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.MINUTES)
            .connectTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.MINUTES)
            .addInterceptor(NetworkInterceptor(application))
            .addInterceptor(getHeaderInterceptor())
            .build()


    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(providesJson())).build()
    }

    @Provides
    @Singleton
    fun providesApiClient(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

    private class NetworkInterceptor(var application: Context) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            if (!isNetworkAvailable(application))
                throw NoInternetException(application.getString(R.string.noInternetConnection))
            return chain.proceed(chain.request())
        }

    }

    private fun getHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request =
                chain.request().newBuilder()
                    .header("Accept-Version", "v1")
                    .header("Client-ID", BuildConfig.UNSPLASH_ACCESS_KEY)
                    .build()
            chain.proceed(request)
        }
    }

    fun fetchErrorMessage(context: Context, throwable: Throwable?): String? {
        val errorMsg: String = if (!isNetworkAvailable(context)) {
            context.getString(R.string.noInternetConnection)
        } else if (throwable is IOException) {
            context.getString(R.string.serverError)
        } else if (throwable is IllegalStateException) {
            context.getString(R.string.parseError)
        } else {
            context.getString(R.string.textErrorMessage)
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