package com.zstu.natsukawa.fasterner.main;


import android.content.Context;
import android.location.Location;
import com.alibaba.fastjson.JSONObject;
import com.zstu.natsukawa.fasterner.base.DataManager;
import com.zstu.natsukawa.fasterner.personalinfo.PersonalInfoBean;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

class MainPresenter extends MainContract.MainPresenter {

    private JSONObject returnedJson;
    private PersonalInfoBean personalInfoBean;

    MainPresenter(Context context, String url) {
        attachModel(new MainModel(new DataManager(context, url)));
        attachToView((MainContract.MainView) context);
    }

    @Override
    void attendance(String studentNumber, Location location) {
        getCompositeDisposable().add(getModel().attendance(studentNumber,location)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<JSONObject>() {
            @Override
            public void onNext(JSONObject jsonObject) {
                returnedJson = jsonObject;
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                getView().showSuccess(returnedJson.toString());
            }
        }));
    }


    void getStudentInfo(String studentNumber){
        getCompositeDisposable().add(getModel().getStudentInfo(studentNumber)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<PersonalInfoBean>() {
            @Override
            public void onNext(PersonalInfoBean tempPersonalInfoBean) {
                personalInfoBean = tempPersonalInfoBean;
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                getView().getPersonalInfoBean(personalInfoBean);
            }
        }));
    }
}
