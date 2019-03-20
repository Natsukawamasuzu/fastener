package com.zstu.natsukawa.fasterner.register;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.zstu.natsukawa.fasterner.base.BaseModelInterface;
import com.zstu.natsukawa.fasterner.base.BasePresenter;
import com.zstu.natsukawa.fasterner.base.BaseViewInterface;
import io.reactivex.Observable;


public interface RegisterContract {

    interface RegisterModel extends BaseModelInterface{
        Observable<RegisterBean> registerToDatabase(RegisterBean registerBean);
        void saveUserInfo(Context context, RegisterBean registerBean);
    }

    interface RegisterView extends BaseViewInterface{
        void showErrorStudentNumber(String errorInfo);
        void showErrorPhoneNumber(String errorInfo);
        void showErrorPassword(String errorInfo);
        void showErrorConfirmation(String errorInfo);
        void showErrorName(String errorInfo);
        void canRegister(boolean[] flags);
        void showLoading();
        void showSuccess(String result);
        void showError();
    }

    abstract class RegisterPresenter extends BasePresenter<RegisterModel, RegisterView>{
        public abstract void register (String... registerData);
        public abstract void checkValidStudentNumber(String studentNumber);
        public abstract void checkValidPhoneNumber(String phoneNumber);
        public abstract void checkValidPassword(String password);
        public abstract void checkConsistentPassword(String password, String passwordConfirming);
        public abstract void checkNullName(String studentName);
        public abstract void canRegister();
    }

}
