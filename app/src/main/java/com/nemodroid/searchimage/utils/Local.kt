package com.nemodroid.searchimage.utils

import android.content.Context
import android.content.res.Configuration
import com.nemodroid.searchimage.utils.prefs.SharedPreferenceUtils
import java.util.*
import android.os.Build

import android.annotation.TargetApi
import com.nemodroid.searchimage.R


object Local {

    fun updateConfig(context: Context, sLocale: String?) {
        val locale = Locale(sLocale)
        Locale.setDefault(locale)
        val config = Configuration()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSystemLocale(config, locale)
        } else {
            setSystemLocaleLegacy(config, locale)
        }
    }

    fun getStringByLanguage(context: Context, language: String, resID: Int): String? {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(Locale(language.lowercase(Locale.getDefault())))
        return context.createConfigurationContext(configuration).resources.getString(resID)
    }

    fun isArabic(context: Context): Boolean {
        val isArabic: Boolean
        val sharedPreferenceUtils = SharedPreferenceUtils(context)
        val language: String = sharedPreferenceUtils.getStringValue(
            context.getString(R.string.appConstantDefaultLanguage), ""
        )!!
        isArabic = language.equals("ar", ignoreCase = true)
        return isArabic
    }

    private fun setSystemLocaleLegacy(config: Configuration, locale: Locale?) {
        config.locale = locale
    }

    @TargetApi(Build.VERSION_CODES.N)
    fun setSystemLocale(config: Configuration, locale: Locale?) {
        config.setLocale(locale)
    }
}