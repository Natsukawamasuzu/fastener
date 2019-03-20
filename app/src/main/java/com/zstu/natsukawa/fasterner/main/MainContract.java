package com.zstu.natsukawa.fasterner.main;

import android.location.Location;
import com.alibaba.fastjson.JSONObject;
import com.zstu.natsukawa.fasterner.base.BaseModelInterface;
import com.zstu.natsukawa.fasterner.base.BasePresenter;
import com.zstu.natsukawa.fasterner.base.BaseViewInterface;
import com.zstu.natsukawa.fasterner.personalinfo.PersonalInfoBean;
import io.reactivex.Observable;


public interface MainContract {

    interface MainView extends BaseViewInterface {
        void showSuccess(String result);
        void getPersonalInfoBean(PersonalInfoBean personalInfoBean);
    }
    interface MainModel extends BaseModelInterface {
        Observable<JSONObject> attendance (String studentNumber, Location location);
        Observable<PersonalInfoBean> getStudentInfo(String studentNumber);
    }
    abstract class MainPresenter extends BasePresenter<MainModel,MainView>{
        abstract void attendance(String studentNumber, Location location);
    }

}
