package com.zstu.natsukawa.fasterner.login;

import android.content.Context;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.zstu.natsukawa.fasterner.base.DataManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class LoginPresenter extends LoginContract.LoginPresenter {
    private JSONObject jsonObject;
    private boolean[] flags = {false, false};
    private Context context;

    LoginPresenter(Context context, String url) {
        attachModel(new LoginModel(new DataManager(context, url)));
        attachToView((LoginContract.LoginView) context);
        this.context = context;
    }

    @Override
    public void login(final String studentNumber, final String password) {
        getCompositeDisposable().add(getModel().login(studentNumber, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<JSONObject>() {

                    @Override
                    public void onNext(JSONObject returnedJsonObject) {
                        jsonObject = returnedJsonObject;
                    }

                    @Override
                    public void onError(Throwable e) {
                        LoginPresenter.this.getView().showError("似乎出了些问题...");
                    }

                    @Override
                    public void onComplete() {
                        Integer code = jsonObject.getInteger("code");
                        switch (code){
                            case 200:
                                getModel().refreshToken(context,jsonObject.getJSONObject("extra").getString("auth"));
                                getView().showSuccess(null);
                                break;
                            case 400:
                                getView().showError(jsonObject.getJSONObject("extra").getString("reason"));
                                break;
                            case 401:
                                getView().showError("未授权");
                                break;
                            case 403:
                                getView().showError("Forbidden");
                                break;
                            case 404:
                                getView().showError("网络似乎有些问题");
                                break;
                            default: break;
                        }
                    }
                }));
    }

    @Override
    public void isValidStudentNumber(String studentNumber) {
        if (null == studentNumber) {
            LoginPresenter.this.getView().alertInvalidStudentNumber("学号不可以为空");
            flags[0] = false;
            return;
        } else if (studentNumber.length() <= 5) {
            LoginPresenter.this.getView().alertInvalidStudentNumber("学号必须是13位");
            flags[0] = false;
            return;
        }
        LoginPresenter.this.getView().alertInvalidStudentNumber(null);
        flags[0] = true;
    }

    @Override
    public void isValidStudentPassword(String password) {
        if (null == password) {
            LoginPresenter.this.getView().alertInvalidStudentPassword("密码不可以为空");
            flags[1] = false;
            return;
        } else {
            int length = password.length();
            if (length < 6 || length > 16) {
                LoginPresenter.this.getView().alertInvalidStudentPassword("密码长度必须介于6-16位");
                flags[1] = false;
                return;
            }
        }
        LoginPresenter.this.getView().alertInvalidStudentPassword(null);
        flags[1] = true;
    }

    @Override
    public void canLogin() {
        LoginPresenter.this.getView().canRegister(flags);
    }

    @Override
    protected void cancelRequest() {
        super.cancelRequest();
        LoginPresenter.this.getView().cancelRequest();
    }
}
