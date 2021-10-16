package com.entreprisecorp.messagereact

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class ReactMessage : Application() {

    private val sharedPrefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    var channelTwitch: String = ""
        set(value) {
            field = value
            sharedPrefs.edit {
                putString(SHARED_PREFS_CHANNEL, value)
            }
        }
        get() {
            return sharedPrefs.getString(SHARED_PREFS_CHANNEL, null) ?: ""
        }

    var ipAddress: String = ""
        set(value) {
            field = value
            sharedPrefs.edit {
                putString(SHARED_PREFS_IP, value)
            }
        }
        get() {
            return sharedPrefs.getString(
                SHARED_PREFS_IP,
                null
            ) ?: ""
        }


    companion object {
        const val SHARED_PREFS_IP: String = "id_adress_server"
        const val SHARED_PREFS_CHANNEL: String = "channel_name"
    }
}