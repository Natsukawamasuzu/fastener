package com.zstu.natsukawa.fasterner.fragments.askforleave;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zstu.natsukawa.fastener.R;


import java.util.List;

public class AskForLeaveItemAdapter extends BaseQuickAdapter<AskForLeaveBean, BaseViewHolder> {

    private Context context;

    public AskForLeaveItemAdapter(Context context, int layoutResId, @Nullable List<AskForLeaveBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AskForLeaveBean item) {
        switch (item.getAuditingState()) {
            case 0:
                helper.setText(R.id.auditing_state_indicator_text, "正在审核")
                        .setTextColor(R.id.auditing_state_indicator_text, ContextCompat.getColor(context, R.color.auditing_processing_color))
                        .setImageResource(R.id.auditing_state_icon, R.mipmap.auditing_processing)
                        .setBackgroundColor(R.id.auditing_state_indicator_bar, ContextCompat.getColor(context, R.color.auditing_processing_color));
                break;
            case 1:
                helper.setText(R.id.auditing_state_indicator_text, "审核通过")
                        .setTextColor(R.id.auditing_state_indicator_text, ContextCompat.getColor(context, R.color.auditing_passed_color))
                        .setImageResource(R.id.auditing_state_icon, R.mipmap.auditing_passed)
                        .setBackgroundColor(R.id.auditing_state_indicator_bar, ContextCompat.getColor(context, R.color.auditing_passed_color));
                break;
            case 2:
                helper.setText(R.id.auditing_state_indicator_text, "审核驳回")
                        .setTextColor(R.id.auditing_state_indicator_text, ContextCompat.getColor(context, R.color.auditing_rejected_color))
                        .setImageResource(R.id.auditing_state_icon, R.mipmap.auditing_rejected)
                        .setBackgroundColor(R.id.auditing_state_indicator_bar, ContextCompat.getColor(context, R.color.auditing_rejected_color));
                break;
            default:
                break;
        }
        helper.setText(R.id.ask_for_leave_time_text, item.getAskForLeaveStartTime())
                .setText(R.id.ask_for_leave_reason_content, item.getAskForLeaveReason())
                .setText(R.id.ask_for_leave_phone_number_content, item.getContactNumber());
    }
}
