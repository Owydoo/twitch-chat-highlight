package com.entreprisecorp.messagereact.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.entreprisecorp.messagereact.NavMainDirections
import com.entreprisecorp.messagereact.R
import com.entreprisecorp.messagereact.ReactMessage
import com.entreprisecorp.messagereact.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import io.socket.client.Socket

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        snackBarIpServer()
    }

    private fun snackBarIpServer() {
        val ipAddress = (application as ReactMessage).reactMessageDatasource.ipAddress
        if (ipAddress != null) {
            Snackbar.make(this, binding.root, ipAddress, Snackbar.LENGTH_LONG).show()
        }
    }
}