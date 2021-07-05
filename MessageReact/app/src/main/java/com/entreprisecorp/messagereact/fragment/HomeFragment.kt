package com.entreprisecorp.messagereact.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.core.view.updatePadding
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
    private val interpolator = DecelerateInterpolator()

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
                    isVisible = !it
                }
            })
        }

        viewModel.messages.observe(viewLifecycleOwner) {
            refreshChat(it)
        }

        viewModel.displayedMessage.observe(viewLifecycleOwner) {
            if (it == null) {
                hideMessageLayout()
            } else {
                showMessageLayout(it)
            }
        }
    }

    private fun showMessageLayout(chatMessage: ChatMessage) {
        binding.apply {
            include.messageTextView.text = chatMessage.message
            include.usernameTextView.text = chatMessage.username
            include.hideMessageButton.setOnClickListener {
                viewModel.hideMessage()
            }
        }
        val displayedMessageLayout = binding.displayedMessageLayout
        displayedMessageLayout.post {
            context?.let {
                displayedMessageLayout.isVisible = true
                displayedMessageLayout.animate().apply {
                    duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
                    interpolator = this@HomeFragment.interpolator
                    setUpdateListener {
                        binding.recyclerView.updatePadding(top = (displayedMessageLayout.height.toFloat() * it.animatedValue as Float).toInt())
                    }
                    translationY(0f)
                    start()
                }
            }
        }
    }

    private fun hideMessageLayout() {
        binding.apply {
            include.messageTextView.text = null
            include.usernameTextView.text = null
            include.hideMessageButton.setOnClickListener { null }
        }
        val displayedMessageLayout = binding.displayedMessageLayout
        displayedMessageLayout.post {
            context?.let {
                displayedMessageLayout.isVisible = true
                displayedMessageLayout.animate().apply {
                    duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
                    interpolator = this@HomeFragment.interpolator
                    translationY(-displayedMessageLayout.height.toFloat())
                    setUpdateListener {
                        binding.recyclerView.updatePadding(top = (displayedMessageLayout.height.toFloat() * (1f - it.animatedValue as Float)).toInt())
                    }
                    translationY(-displayedMessageLayout.height.toFloat())
                    start()
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