package com.zstu.natsukawa.fasterner.msgmangement;

import android.content.Context;
import androidx.fragment.app.Fragment;
import com.zstu.natsukawa.fasterner.base.DataManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

class MessagePresenter extends MessageContract.MessagePresenter {

    MessagePresenter(Fragment fragment, String url) {
        attachModel(new MessageModel(new DataManager(fragment.getContext(), url)));
        attachToView((MessageContract.MessageView) fragment);
    }

    @Override
    ArrayList<MessageEntity> getMessages(@NotNull String studentNumber) {
        ArrayList<MessageEntity> messages = new ArrayList<>();
        getCompositeDisposable().add(getModel().getMessages(studentNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<MessageEntity>>() {
                    @Override
                    public void onNext(ArrayList<MessageEntity> messageEntity) {
                        messages.addAll(messageEntity);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (messages.size() != 0)
                            MessagePresenter.this.getView().showSuccess(messages);
                        else
                            MessagePresenter.this.getView().showEmptyMessageList();
                    }
                }));
        return messages;
    }
}
