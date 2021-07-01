package com.entreprisecorp.messagereact.fastitems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entreprisecorp.messagereact.ChatMessage
import com.entreprisecorp.messagereact.R
import com.entreprisecorp.messagereact.databinding.ItemMessageBinding
import com.entreprisecorp.messagereact.extensions.addRipple
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class MessageItem : AbstractBindingItem<ItemMessageBinding>() {
    override val type: Int = R.id.item_message

    var chatMessage: ChatMessage = ChatMessage("", "")
    var onClick: View.OnClickListener? = null

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemMessageBinding {
        return ItemMessageBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemMessageBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)

        binding.apply {
            usernameTextView.text = chatMessage?.username
            messageTextView.text = chatMessage?.message
            root.setOnClickListener(onClick)
        }
    }

    override fun unbindView(binding: ItemMessageBinding) {
        super.unbindView(binding)

        binding.apply {
            usernameTextView.text = null
            messageTextView.text = null
            root.setOnClickListener(null)
        }
    }

}

fun messageItem(block: MessageItem.() -> Unit): MessageItem = MessageItem().apply(block)