package com.zstu.natsukawa.fasterner.register;

import android.content.Context;
import android.util.Log;
import com.zstu.natsukawa.fasterner.base.DataManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class RegisterPresenter extends RegisterContract.RegisterPresenter {

    private boolean [] flags = {false, false, false, false, false};
    private Context context;
    private String onBackMessage;

    RegisterPresenter(Context context, String url){
        attachModel(new RegisterModel(new DataManager(context,url)));
        attachToView((RegisterContract.RegisterView) context);
        this.context = context;
    }

    @Override
    public void register(String... registerData) {
        RegisterBean studentBean = new RegisterBean(registerData[0],registerData[3], registerData[2], registerData[1]);
        Log.d("RegisterPresenter",studentBean.toString());
        RegisterPresenter.this.getCompositeDisposable()
                .add(this.getModel().registerToDatabase(studentBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<RegisterBean>() {
                    @Override
                    public void onNext(RegisterBean registerBean) {
                        onBackMessage = registerBean.toString();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        RegisterPresenter.this.getModel().saveUserInfo(context,studentBean);
                        RegisterPresenter.this.getView().showSuccess(onBackMessage);
                    }
                }));

    }

    @Override
    public void checkValidStudentNumber(String studentNumber) {
        if(null == studentNumber){
            RegisterPresenter.this.getView().showErrorStudentNumber("学号不可以为空");
            flags[0] = false;
            return;
        }
        else if(studentNumber.length() != 13){
            RegisterPresenter.this.getView().showErrorStudentNumber("学号长度必须为13位");
            flags[0] = false;
            return;
        }
        RegisterPresenter.this.getView().showErrorStudentNumber(null);
        flags[0] = true;
    }

    @Override
    public void checkValidPhoneNumber(String phoneNumber) {
        if(null == phoneNumber){
            RegisterPresenter.this.getView().showErrorPhoneNumber("手机号码不可以为空");
            flags[1] = false;
            return;
        }
        else if(phoneNumber.length() != 11){
            RegisterPresenter.this.getView().showErrorPhoneNumber("手机号码长度必须为11位");
            flags[1] = false;
            return;
        }
        RegisterPresenter.this.getView().showErrorPhoneNumber(null);
        flags[1] = true;
    }

    @Override
    public void checkValidPassword(String password) {
        if(null == password) {
            RegisterPresenter.this.getView().showErrorPassword("密码不可以为空");
            flags[2] = false;
            return;
        }
        else{
            int length = password.length();
            if(length < 6 || length > 16) {
                RegisterPresenter.this.getView().showErrorPassword("密码长度必须介于6-16位");
                flags[2] = false;
                return;
            }
        }
        RegisterPresenter.this.getView().showErrorPassword(null);
        flags[2] = true;
    }

    @Override
    public void checkConsistentPassword(String password, String passwordConfirming) {
        if(!password.equals(passwordConfirming)){
            RegisterPresenter.this.getView().showErrorConfirmation("两次输入的密码不一致");
            flags[3] = false;
            return;
        }
        RegisterPresenter.this.getView().showErrorConfirmation(null);
        flags[3] = true;
    }

    @Override
    public void checkNullName(String studentName) {
        if("".equals(studentName)){  //判断条件里的空串不能改成null，不然会失效，我也不知道为什么，其他的就没问题
            RegisterPresenter.this.getView().showErrorName("姓名不能为空");
            flags[4] = false;
            return;
        }
        RegisterPresenter.this.getView().showErrorName(null);
        flags[4] = true;

    }

    @Override
    public void canRegister() {
        RegisterPresenter.this.getView().canRegister(flags);
    }
}
