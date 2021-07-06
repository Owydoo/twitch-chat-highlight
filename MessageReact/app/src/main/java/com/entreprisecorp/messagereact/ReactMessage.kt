package com.entreprisecorp.messagereact

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import io.socket.client.IO
import io.socket.client.Socket
import java.lang.Exception
import java.net.URISyntaxException

class ReactMessage : Application() {

    lateinit var socket: Socket
    var ipAddress: String? = ""
        set(value) {
            field = value
            sharedPrefs.edit {
                putString(SHARED_PREFS_IP, value)
            }
        }
        get() {
            return sharedPrefs.getString(SHARED_PREFS_IP, "http://192.168.1.1:3000")
        }

    private val sharedPrefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onCreate() {
        super.onCreate()
        try {
            socket = IO.socket(ipAddress)
            socket.connect()
        } catch (e: Exception) {
            Log.d("chat", e.toString())
        }
    }

    fun changeSocket(ipAddress: String) {
        try {
            socket = IO.socket(ipAddress)
            socket.connect()
            this.ipAddress = ipAddress
        } catch (e: URISyntaxException) {
            Log.d("chat", e.toString())
        }
    }

    companion object {
        const val SHARED_PREFS_IP: String = "id_adress_server"
    }
}