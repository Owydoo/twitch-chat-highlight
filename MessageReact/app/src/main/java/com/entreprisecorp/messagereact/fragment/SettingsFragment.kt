package com.entreprisecorp.messagereact.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.entreprisecorp.messagereact.R
import com.entreprisecorp.messagereact.ReactMessage
import com.entreprisecorp.messagereact.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.settings)

        binding = FragmentSettingsBinding.bind(view)
        binding.ipEditText.setText((activity?.application as ReactMessage).ipAddress)
        binding.channelEditText.setText((activity?.application as ReactMessage).channelTwitch)

        binding.changeServerButton.setOnClickListener {
            val ipAddress = binding.ipEditText.text.toString()
            val channel = binding.channelEditText.text.toString()
            (activity?.application as ReactMessage).ipAddress = ipAddress
            (activity?.application as ReactMessage).channelTwitch = channel
            findNavController().navigateUp()
        }

    }
}