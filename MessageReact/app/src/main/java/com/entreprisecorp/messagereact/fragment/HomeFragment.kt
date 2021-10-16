package com.entreprisecorp.messagereact.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.entreprisecorp.data.ChatMessage
import com.entreprisecorp.messagereact.NavMainDirections
import com.entreprisecorp.messagereact.R
import com.entreprisecorp.messagereact.ReactMessage
import com.entreprisecorp.messagereact.databinding.FragmentHomeBinding
import com.entreprisecorp.messagereact.recyclerview.MarginRecyclerViewDecoration
import com.entreprisecorp.messagereact.recyclerview.ScrollToTopDataObserver
import com.entreprisecorp.messagereact.fastitems.messageItem
import com.entreprisecorp.messagereact.main.MainActivity
import com.entreprisecorp.messagereact.viewModel.HomeViewModel
import com.mikepenz.fastadapter.adapters.GenericFastItemAdapter
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val fastAdapter = GenericFastItemAdapter()
    private val interpolator = DecelerateInterpolator()

    private val viewModel: HomeViewModel by activityViewModels()

    private fun redirectToSettingsIfNeeded(): Boolean {
        val ipAddress = (activity?.application as ReactMessage).ipAddress
        return if (ipAddress == "") {
            (activity as MainActivity).showSnackBar(
                getString(R.string.ask_for_settings), R.color.dark_blue
            )
            findNavController().navigate(NavMainDirections.actionGlobalSettingsFragment())
            false
        } else {
            true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        if (redirectToSettingsIfNeeded()) {
            initRepository()
        }
        setHasOptionsMenu(true)
        setupActionBar()

        binding.scrollToEndButton.apply {
            isVisible = false
            setOnClickListener {
                isVisible = false
                binding.recyclerView.smoothScrollToPosition(fastAdapter.itemCount)
            }
        }

        binding.recyclerView.apply {
            val layoutManagerRC = LinearLayoutManager(activity)
            layoutManager = layoutManagerRC
            adapter = fastAdapter

            addItemDecoration(
                MarginRecyclerViewDecoration(resources.getDimensionPixelSize(R.dimen.spacing_medium))
            )
            fastAdapter.registerAdapterDataObserver(ScrollToTopDataObserver(layoutManagerRC, this) {
                binding.scrollToEndButton.apply {
                    isVisible = !it
                }
            })
        }

        viewModel.isConnected.observe(viewLifecycleOwner) {
            (activity as MainActivity).showSnackBar(getString(R.string.connected), R.color.green)
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

    private fun setupActionBar() {
        val title =
            (activity?.application as ReactMessage).channelTwitch.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
        (activity as AppCompatActivity).supportActionBar?.title = title
        (activity as AppCompatActivity).supportActionBar?.setIcon(R.drawable.logo_toolbar)
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
                    duration =
                        resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
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
            include.hideMessageButton.setOnClickListener(null)
        }
        val displayedMessageLayout = binding.displayedMessageLayout
        displayedMessageLayout.post {
            context?.let {
                displayedMessageLayout.isVisible = true
                displayedMessageLayout.animate().apply {
                    duration =
                        resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_profile -> {
                findNavController().navigate(NavMainDirections.actionGlobalSettingsFragment())
            }
            R.id.action_refresh -> {
                fastAdapter.setNewList(arrayListOf())
                initRepository()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_settings, menu)
    }

    private fun refreshChat(chat: List<ChatMessage>) {
        chat.lastOrNull()?.let { message ->
            fastAdapter.add(
                messageItem {
                    chatMessage = message
                    onClick = View.OnClickListener {
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToMessageActionBottomSheetDialogFragment(
                                message
                            )
                        )
                    }
                }
            )
        }
    }

    private fun initRepository() {
        viewModel.initSocketRepository(
            (activity?.application as ReactMessage).ipAddress,
            (activity?.application as ReactMessage).channelTwitch,
        )
    }
}