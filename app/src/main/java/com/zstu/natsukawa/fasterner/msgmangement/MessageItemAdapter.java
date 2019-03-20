package com.zstu.natsukawa.fasterner.msgmangement;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zstu.natsukawa.fastener.R;

import java.util.List;

public class MessageItemAdapter extends BaseQuickAdapter<MessageEntity, BaseViewHolder> {
    public MessageItemAdapter(int layoutResId, @Nullable List<MessageEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageEntity item) {
        helper.setText(R.id.message_time, item.getMessageTime())
                .setText(R.id.message_content, item.getMessageContent());
    }
}
