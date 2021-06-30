package com.entreprisecorp.messagereact.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.navigation.fragment.navArgs
import com.entreprisecorp.messagereact.databinding.MessageActionBottomSheetDialogFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MessageActionBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: MessageActionBottomSheetDialogFragmentBinding
    private val args: MessageActionBottomSheetDialogFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MessageActionBottomSheetDialogFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.usernameTextView.text = args.message.username
        binding.messageTextView.text = args.message.message
    }


}