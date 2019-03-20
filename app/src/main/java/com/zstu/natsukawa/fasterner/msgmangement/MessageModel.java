package com.zstu.natsukawa.fasterner.msgmangement;

import com.zstu.natsukawa.fasterner.base.APIService;
import com.zstu.natsukawa.fasterner.base.DataManager;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;

public class MessageModel implements MessageContract.MessageModel{

    private DataManager dataManager;

    public MessageModel(DataManager dataManager){
        this.dataManager = dataManager;
    }


    @Override
    public Observable<ArrayList<MessageEntity>> getMessages(String studentNumber) {
        return dataManager.getMessages(studentNumber);
    }
}
