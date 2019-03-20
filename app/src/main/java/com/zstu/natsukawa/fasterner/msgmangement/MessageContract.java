package com.zstu.natsukawa.fasterner.msgmangement;

import androidx.fragment.app.Fragment;
import com.zstu.natsukawa.fasterner.base.BaseModelInterface;
import com.zstu.natsukawa.fasterner.base.BasePresenter;
import com.zstu.natsukawa.fasterner.base.BaseViewInterface;
import io.reactivex.Observable;

import java.util.ArrayList;


public interface MessageContract {

    interface MessageView extends BaseViewInterface{
        void showEmptyMessageList();
        void showSuccess(ArrayList<MessageEntity> messages);
    }
    interface MessageModel extends BaseModelInterface{
        Observable<ArrayList<MessageEntity>> getMessages(String studentNumber);
    }
    abstract class MessagePresenter extends BasePresenter<MessageModel, MessageView>{
        abstract ArrayList<MessageEntity> getMessages(String studentNumber);
    }

}
