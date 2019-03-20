package com.zstu.natsukawa.fasterner.main;

import android.location.Location;
import com.alibaba.fastjson.JSONObject;
import com.zstu.natsukawa.fasterner.base.DataManager;
import com.zstu.natsukawa.fasterner.personalinfo.PersonalInfoBean;
import io.reactivex.Observable;

public class MainModel implements MainContract.MainModel {

    private DataManager dataManager;
    public MainModel(DataManager dataManager){
        this.dataManager = dataManager;
    }

    @Override
    public Observable<JSONObject> attendance(String studentNumber, Location location) {
        return dataManager.attendance(studentNumber,location);
    }

    public Observable<PersonalInfoBean> getStudentInfo(String studentNumber){
        return dataManager.getPersonalInfo(studentNumber);
    }

}
