package com.entreprisecorp.messagereact.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.entreprisecorp.messagereact.ChatMessage
import com.entreprisecorp.messagereact.R
import com.entreprisecorp.messagereact.databinding.FragmentHomeBinding
import com.entreprisecorp.messagereact.extensions.closeKeyboardOnScroll
import com.entreprisecorp.messagereact.fastitems.messageItem
import com.entreprisecorp.messagereact.main.MainActivity
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.GenericFastItemAdapter
import io.socket.client.Socket

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding
    private val fastAdapter = GenericFastItemAdapter()
    private val items = ArrayList<GenericItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            closeKeyboardOnScroll(activity)
            adapter = fastAdapter
        }
        fillAdapter()
    }


    fun fillAdapter() {
        MainActivity.listMessage.forEach{ chatMessage ->
            items += messageItem {
                this.username = chatMessage.username
                this.message = chatMessage.message
                onClick = View.OnClickListener {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMessageActionBottomSheetDialogFragment(chatMessage))
                    fillAdapter()
                }
            }
        }

        fastAdapter.setNewList(items)

    }
}