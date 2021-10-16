package com.entreprisecorp.messagereact.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.entreprisecorp.messagereact.databinding.MessageActionBottomSheetDialogFragmentBinding
import com.entreprisecorp.messagereact.viewModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MessageActionBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: MessageActionBottomSheetDialogFragmentBinding
    private val args: MessageActionBottomSheetDialogFragmentArgs by navArgs()

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MessageActionBottomSheetDialogFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            usernameTextView.text = args.message.username
            messageTextView.text = args.message.message
            DisplayOnStreamButton.setOnClickListener {
                viewModel.sendChatToStream(args.message)
                dismiss()
            }
        }
    }

}