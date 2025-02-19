package io.getstream.chat.android.ui.message.list.adapter.viewholder.decorator.internal

import android.widget.TextView
import com.getstream.sdk.chat.adapter.MessageListItem
import io.getstream.chat.android.ui.message.list.adapter.viewholder.internal.GiphyViewHolder
import io.getstream.chat.android.ui.message.list.adapter.viewholder.internal.MessagePlainTextViewHolder
import io.getstream.chat.android.ui.message.list.adapter.viewholder.internal.TextAndAttachmentsViewHolder
import io.getstream.chat.android.ui.message.list.internal.MessageListItemStyle

internal class TextDecorator(val style: MessageListItemStyle) : BaseDecorator() {
    override fun decorateTextAndAttachmentsMessage(
        viewHolder: TextAndAttachmentsViewHolder,
        data: MessageListItem.MessageItem,
    ) = setupTextView(viewHolder.binding.messageText, data)

    override fun decoratePlainTextMessage(
        viewHolder: MessagePlainTextViewHolder,
        data: MessageListItem.MessageItem,
    ) = setupTextView(viewHolder.binding.messageText, data)

    override fun decorateGiphyMessage(
        viewHolder: GiphyViewHolder,
        data: MessageListItem.MessageItem,
    ) = Unit

    private fun setupTextView(textView: TextView, data: MessageListItem.MessageItem) {
        style.getStyleTextColor(data.isMine)?.let { textColor ->
            textView.setTextColor(textColor)
        }

        style.getStyleLinkTextColor(data.isMine)?.let { linkTextColor ->
            textView.setLinkTextColor(linkTextColor)
        }
    }
}
