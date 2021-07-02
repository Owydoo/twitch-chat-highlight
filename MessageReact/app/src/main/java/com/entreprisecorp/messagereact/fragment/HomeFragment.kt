package com.entreprisecorp.messagereact.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.entreprisecorp.messagereact.ChatMessage
import com.entreprisecorp.messagereact.R
import com.entreprisecorp.messagereact.ReactMessage
import com.entreprisecorp.messagereact.databinding.FragmentHomeBinding
import com.entreprisecorp.messagereact.extensions.closeKeyboardOnScroll
import com.entreprisecorp.messagereact.fastitems.messageItem
import com.entreprisecorp.messagereact.viewModel.HomeViewModel
import com.entreprisecorp.messagereact.viewModel.HomeViewModelFactory
import com.mikepenz.fastadapter.adapters.GenericFastItemAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding
    private val fastAdapter = GenericFastItemAdapter()

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(
            application = activity?.application as ReactMessage
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            closeKeyboardOnScroll(activity)
            adapter = fastAdapter
        }

        viewModel.messages.observe(viewLifecycleOwner) {
            refreshChat(it)
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
        binding.recyclerView.smoothScrollToPosition(chat.size)
    }
}