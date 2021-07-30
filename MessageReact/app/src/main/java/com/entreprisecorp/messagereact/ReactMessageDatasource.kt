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

    var channelTwitch: String = ""
        set(value) {
            field = value
            sharedPrefs.edit {
                putString(SHARED_PREFS_CHANNEL, value)
            }
        }
        get() {
            return sharedPrefs.getString(SHARED_PREFS_CHANNEL, null) ?: "sardoche"
        }

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
            initChannel(channelTwitch)

        } catch (e: Exception) {
            Log.d("chat", e.toString())
        }
    }

    fun initChannel(channel: String) {
        socket.on("connected") {
            socket.emit("sendChannelName", channel)
        }
    }

    fun changeSocket(ipAddress: String, channel: String) {
        try {
            socket = IO.socket(ipAddress)
            socket.connect()
            initChannel(channel)
            this.ipAddress = ipAddress
            this.channelTwitch = channel
        } catch (e: URISyntaxException) {
            Log.d("chat", e.toString())
        }
    }

    companion object {
        const val SHARED_PREFS_IP: String = "id_adress_server"
        const val SHARED_PREFS_CHANNEL: String = "channel_name"
        const val DEFAULT_IP: String = "http://192.168.1.1:3000"
    }

}