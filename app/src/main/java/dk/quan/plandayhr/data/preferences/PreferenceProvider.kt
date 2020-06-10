package dk.quan.plandayhr.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PreferenceProvider(
    context: Context
) {
    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun put(key: String, value: Long) {
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun get(key: String, defaultValue: Long): Long {
        return preference.getLong(key, defaultValue)
    }

    fun put(key: String, value: String) {
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun get(key: String, defaultValue: String?): String? {
        return preference.getString(key, defaultValue)
    }

    companion object {
        const val TOKEN_EXPIRES_AT = "token_expires_at"
        const val TOKEN = "token"
    }
}