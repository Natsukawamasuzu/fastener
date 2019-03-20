package com.zstu.natsukawa.fasterner.register;

import android.content.Context;
import android.content.SharedPreferences;
import com.zstu.natsukawa.fasterner.base.DataManager;
import com.zstu.natsukawa.fasterner.base.Datas;
import io.reactivex.Observable;

public class RegisterModel implements RegisterContract.RegisterModel {

    private DataManager dataManager;

    RegisterModel(DataManager dataManager){
        this.dataManager = dataManager;
    }

    @Override
    public Observable<RegisterBean> registerToDatabase(RegisterBean registerBean) {
        return dataManager.register(registerBean);
    }

    @Override
    public void saveUserInfo(Context context, RegisterBean registerBean) {
        SharedPreferences sp = context.getSharedPreferences(Datas.SP_NAME,Context.MODE_PRIVATE);
        sp.edit().putString("studentNumber", registerBean.getStudentNumber())
                .putString("studentName",registerBean.getStudentName())
                .putString("studentPhone",registerBean.getPhoneNumber())
                .putString("studentMemberLevel",registerBean.getStudentMemberLevel())
                .putString("studentRoleInTeam", "null")
                .putFloat("technology",0)
                .putFloat("characteristic",0)
                .putFloat("otherAbility",0)
                .putFloat("professionalAbility",0)
                .putFloat("learningAbility",0)
                .putBoolean("firstRun",false)
                .apply();
    }
}
