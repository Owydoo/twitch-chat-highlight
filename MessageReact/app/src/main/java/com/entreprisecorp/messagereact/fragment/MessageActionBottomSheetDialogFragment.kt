package com.entreprisecorp.messagereact.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.entreprisecorp.messagereact.ReactMessage
import com.entreprisecorp.messagereact.databinding.MessageActionBottomSheetDialogFragmentBinding
import com.entreprisecorp.messagereact.viewModel.HomeViewModel
import com.entreprisecorp.messagereact.viewModel.HomeViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MessageActionBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: MessageActionBottomSheetDialogFragmentBinding
    private val args: MessageActionBottomSheetDialogFragmentArgs by navArgs()

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(
            application = activity?.application as ReactMessage
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MessageActionBottomSheetDialogFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.usernameTextView.text = args.message.username
        binding.messageTextView.text = args.message.message

        binding.DisplayOnStreamButton.setOnClickListener {
            viewModel.sendChatToStream(args.message)
        }
    }

}