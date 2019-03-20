package com.zstu.natsukawa.fasterner.progress;

import com.zstu.natsukawa.fasterner.base.BaseModelInterface;
import com.zstu.natsukawa.fasterner.base.BasePresenter;
import com.zstu.natsukawa.fasterner.base.BaseViewInterface;

public interface ProgressContract {
    interface ProgressView extends BaseViewInterface{}
    interface ProgressModel extends BaseModelInterface{}
    abstract class ProgressPresenter extends BasePresenter<ProgressModel,ProgressView>{

    }
}
