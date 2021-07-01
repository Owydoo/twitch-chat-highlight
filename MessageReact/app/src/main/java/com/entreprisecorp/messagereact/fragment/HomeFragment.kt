package com.entreprisecorp.messagereact.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.entreprisecorp.messagereact.ChatMessage
import com.entreprisecorp.messagereact.R
import com.entreprisecorp.messagereact.ReactMessage
import com.entreprisecorp.messagereact.databinding.FragmentHomeBinding
import com.entreprisecorp.messagereact.extensions.closeKeyboardOnScroll
import com.entreprisecorp.messagereact.fastitems.messageItem
import com.entreprisecorp.messagereact.main.MainActivity
import com.entreprisecorp.messagereact.viewModel.HomeViewModel
import com.entreprisecorp.messagereact.viewModel.HomeViewModelFactory
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.GenericFastItemAdapter
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

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