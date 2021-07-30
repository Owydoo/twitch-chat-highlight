package com.entreprisecorp.messagereact.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.entreprisecorp.messagereact.NavMainDirections
import com.entreprisecorp.messagereact.R
import com.entreprisecorp.messagereact.ReactMessage
import com.entreprisecorp.messagereact.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)
        binding.ipEditText.setText((activity?.application as ReactMessage).reactMessageDatasource.ipAddress)
        binding.channelEditText.setText((activity?.application as ReactMessage).reactMessageDatasource.channelTwitch)

        binding.changeServerButton.setOnClickListener {
            val ipAddress = binding.ipEditText.text.toString()
            val channel = binding.channelEditText.text.toString()
            context?.let { it1 -> Snackbar.make(it1, view, "$ipAddress $channel", Snackbar.LENGTH_LONG).show() }
            (activity?.application as ReactMessage).reactMessageDatasource.changeSocket(ipAddress, channel)
            findNavController().navigateUp()
        }

    }
}