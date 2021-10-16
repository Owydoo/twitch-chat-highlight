package com.entreprisecorp.messagereact.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.entreprisecorp.messagereact.R
import com.entreprisecorp.messagereact.ReactMessage
import com.entreprisecorp.messagereact.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    fun showSnackBar(text: String, color: Int) {
        Snackbar.make(this, binding.root, text, Snackbar.LENGTH_LONG)
            .setBackgroundTint(resources.getColor(color, null)).show()
    }
}