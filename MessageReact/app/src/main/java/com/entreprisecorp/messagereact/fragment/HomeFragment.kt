package com.entreprisecorp.messagereact.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.entreprisecorp.messagereact.ChatMessage
import com.entreprisecorp.messagereact.R
import com.entreprisecorp.messagereact.ReactMessage
import com.entreprisecorp.messagereact.databinding.FragmentHomeBinding
import com.entreprisecorp.messagereact.extensions.ScrollToTopDataObserver
import com.entreprisecorp.messagereact.extensions.closeKeyboardOnScroll
import com.entreprisecorp.messagereact.fastitems.messageItem
import com.entreprisecorp.messagereact.viewModel.HomeViewModel
import com.entreprisecorp.messagereact.viewModel.HomeViewModelFactory
import com.mikepenz.fastadapter.adapters.GenericFastItemAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val fastAdapter = GenericFastItemAdapter()

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(
            application = activity?.application as ReactMessage
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.scrollToEndButton.apply {
            isVisible = false
            setOnClickListener {
                isVisible = false
                viewModel.messages.value?.let { it1 -> binding.recyclerView.smoothScrollToPosition(it1.size) }
            }
        }

        binding.recyclerView.apply {
            val layoutManagerRC = LinearLayoutManager(activity)
            layoutManager = layoutManagerRC
            closeKeyboardOnScroll(activity)
            adapter = fastAdapter

            fastAdapter.registerAdapterDataObserver(ScrollToTopDataObserver(layoutManagerRC, this) {
                binding.scrollToEndButton.apply {
                    isVisible = true
                }
            })
        }

        viewModel.messages.observe(viewLifecycleOwner) {
            refreshChat(it)
        }

        viewModel.displayedMessage.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.include.apply {
                    rootDisplayedChatLayout.visibility = View.GONE
                    messageTextView.text = null
                    usernameTextView.text = null
                    hideMessageButton.setOnClickListener(null)
                }
            } else {
                binding.include.apply {
                    rootDisplayedChatLayout.visibility = View.VISIBLE
                    messageTextView.text = it.message
                    usernameTextView.text = it.message
                    hideMessageButton.setOnClickListener {
                        viewModel.hideMessage()
                    }
                }

            }
        }
    }

    private fun refreshChat(chat: ArrayList<ChatMessage>) {
        fastAdapter.add(
            messageItem {
                chatMessage = chat.last().copy()
                onClick = View.OnClickListener {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToMessageActionBottomSheetDialogFragment(
                            chatMessage
                        )
                    )
                }
            }
        )
    }
}