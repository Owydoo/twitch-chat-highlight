package com.example.testsocket.fastitems

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.testsocket.R
import com.example.testsocket.databinding.CardChatItemBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class CardChatItem : AbstractBindingItem<CardChatItemBinding>() {
    override val type: Int = R.id.card_detail

    var username: String? = null
    var message: String? = null

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): CardChatItemBinding {
        return CardChatItemBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: CardChatItemBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)

        binding.apply {
            usernameTextView.text = username
            messageTextView.text = message
        }
    }

    override fun unbindView(binding: CardChatItemBinding) {
        super.unbindView(binding)

        binding.apply {
            usernameTextView.text = null
            messageTextView.text = null
        }
    }
}

fun cardChatItem(block: CardChatItem.() -> Unit): CardChatItem = CardChatItem().apply(block)