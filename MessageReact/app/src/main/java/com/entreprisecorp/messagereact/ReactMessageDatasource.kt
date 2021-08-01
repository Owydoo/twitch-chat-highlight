package com.entreprisecorp.messagereact

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.entreprisecorp.messagereact.main.MainActivity
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class ReactMessageDatasource(private val context: Context) {
    lateinit var socket: Socket
    lateinit var activity: MainActivity

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

    fun initSocket(activity: MainActivity) {
        this.activity = activity
        try {
            socket = IO.socket(ipAddress)
            socket.connect()
            initChannel(channelTwitch)
        } catch (e: Exception) {
            Log.d("chat", e.toString())
        }
    }

    private fun initChannel(channel: String) {
        activity.showSnackBar("$channel, $ipAddress", R.color.primary)
        socket.on("connected") {
            activity.showSnackBar("Connected", R.color.green)
            socket.emit("sendChannelName", channel)
        }
    }

    fun changeSocket(ipAddress: String, channel: String) {
        try {
            socket.disconnect()
            socket = IO.socket(ipAddress)
            socket.connect()
            initChannel(channel)
            this.ipAddress = ipAddress
            this.channelTwitch = channel
        } catch (e: URISyntaxException) {
            Log.d("chat", e.toString())
        }
    }

    fun refreshSocket() {
        try {
            socket = IO.socket(ipAddress)
            socket.connect()
            initChannel(channelTwitch)
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