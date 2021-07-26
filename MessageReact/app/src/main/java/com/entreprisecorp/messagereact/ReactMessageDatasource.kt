package com.entreprisecorp.messagereact

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class ReactMessageDatasource(context: Context) {
    lateinit var socket: Socket

    var ipAddress: String? = ""
        set(value) {
            field = value
            sharedPrefs.edit {
                putString(SHARED_PREFS_IP, value)
            }
        }
        get() {
            return sharedPrefs.getString(SHARED_PREFS_IP, DEFAULT_IP)
        }

    private val sharedPrefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun initSocket() {
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
        const val DEFAULT_IP: String = "http://192.168.1.1:3000"
    }

}