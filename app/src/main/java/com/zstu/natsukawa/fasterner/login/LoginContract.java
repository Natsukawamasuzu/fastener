package com.zstu.natsukawa.fasterner.login;
import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.zstu.natsukawa.fasterner.base.BaseModelInterface;
import com.zstu.natsukawa.fasterner.base.BasePresenter;
import com.zstu.natsukawa.fasterner.base.BaseViewInterface;
import io.reactivex.Observable;

public interface LoginContract {
    interface LoginModel extends BaseModelInterface {
        Observable<JSONObject> login(String userName, String password);
        void refreshToken(Context context, String tokenString);
    }
    interface LoginView extends BaseViewInterface {
        void showLoading();
        void showError(String result);
        void showSuccess(String result);
        void moveToMainUI();
        void moveToRegister();
        void alertInvalidStudentNumber(String alertString);
        void alertInvalidStudentPassword(String alertString);
        void canRegister(boolean [] flags);
        void cancelRequest();
        void loginFailed(String extraMessage);
    }
    abstract class LoginPresenter extends BasePresenter<LoginModel,LoginView>{
        public abstract void login (String studentNumber, String password);
        public abstract void isValidStudentNumber(String studentNumber);
        public abstract void isValidStudentPassword(String password);
        public abstract void canLogin();
    }
}
