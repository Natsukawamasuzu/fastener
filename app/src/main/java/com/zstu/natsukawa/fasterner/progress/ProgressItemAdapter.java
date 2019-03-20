package com.zstu.natsukawa.fasterner.progress;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zstu.natsukawa.fastener.R;

import java.util.List;

public class ProgressItemAdapter extends BaseQuickAdapter<ProgressBean, BaseViewHolder> {

    public ProgressItemAdapter(int layoutResId, @Nullable List<ProgressBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProgressBean item) {
        helper.setText(R.id.progress_time,item.getTime())
                .setText(R.id.progress_content,item.getResult())
                .setText(R.id.progress_source,item.getProgressType());
    }
}
