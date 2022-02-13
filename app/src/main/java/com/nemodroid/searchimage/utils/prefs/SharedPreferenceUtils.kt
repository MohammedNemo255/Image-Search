package com.nemodroid.searchimage.utils.prefs

import android.content.Context
import android.content.SharedPreferences
import com.nemodroid.searchimage.R
import com.nemodroid.searchimage.utils.Local.getStringByLanguage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class SharedPreferenceUtils(context: Context) {
    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils
    private var mSharedPreferences: SharedPreferences = context.getSharedPreferences(
        getStringByLanguage(
            context,
            context.getString(R.string.appLanguageEN),
            R.string.appConstantPrefsName
        ), Context.MODE_PRIVATE
    )
    private var mSharedPreferencesEditor: SharedPreferences.Editor = mSharedPreferences.edit()


    /**
     * Creates single instance of SharedPreferenceUtils
     *
     * @param context context of Activity or Service
     * @return Returns instance of SharedPreferenceUtils
     */
    fun getInstance(context: Context): SharedPreferenceUtils {
        return mSharedPreferenceUtils
    }

    /**
     * Stores String value in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    fun setValue(key: String?, value: String?) {
        mSharedPreferencesEditor!!.putString(key, value)
        mSharedPreferencesEditor!!.commit()
    }

    /**
     * Stores int value in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    fun setValue(key: String?, value: Int) {
        mSharedPreferencesEditor!!.putInt(key, value)
        mSharedPreferencesEditor!!.commit()
    }

    /**
     * Stores Double value in String format in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    fun setValue(key: String?, value: Double) {
        setValue(key, java.lang.Double.toString(value))
    }

    /**
     * Stores long value in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    fun setValue(key: String?, value: Long) {
        mSharedPreferencesEditor!!.putLong(key, value)
        mSharedPreferencesEditor!!.commit()
    }

    /**
     * Stores boolean value in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    fun setValue(key: String?, value: Boolean) {
        mSharedPreferencesEditor!!.putBoolean(key, value)
        mSharedPreferencesEditor!!.commit()
    }

    /**
     * Retrieves String value from preference
     *
     * @param key          key of preference
     * @param defaultValue default value if no key found
     */
    fun getStringValue(key: String?, defaultValue: String?): String? {
        return mSharedPreferences!!.getString(key, defaultValue)
    }

    /**
     * Retrieves int value from preference
     *
     * @param key          key of preference
     * @param defaultValue default value if no key found
     */
    fun getIntValue(key: String?, defaultValue: Int): Int {
        return mSharedPreferences!!.getInt(key, defaultValue)
    }

    /**
     * Retrieves long value from preference
     *
     * @param key          key of preference
     * @param defaultValue default value if no key found
     */
    fun getLongValue(key: String?, defaultValue: Long): Long {
        return mSharedPreferences!!.getLong(key, defaultValue)
    }

    /**
     * Retrieves boolean value from preference
     *
     * @param keyFlag      key of preference
     * @param defaultValue default value if no key found
     */
    fun getBooleanValue(keyFlag: String?, defaultValue: Boolean): Boolean {
        return mSharedPreferences!!.getBoolean(keyFlag, defaultValue)
    }

    /**
     * Removes key from preference
     *
     * @param key key of preference that is to be deleted
     */
    fun removeKey(key: String?) {
        if (mSharedPreferencesEditor != null) {
            mSharedPreferencesEditor!!.remove(key)
            mSharedPreferencesEditor!!.commit()
        }
    }


    /**
     * Return key from preference if exist
     *
     * @param key key of preference that is to be looked for
     */
    fun containsKey(key: String?): Boolean {
        return mSharedPreferences!!.contains(key)
    }

    /**
     * Clears all the preferences stored
     */
    fun clear() {
        mSharedPreferencesEditor!!.clear().commit()
    }
}