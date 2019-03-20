package com.zstu.natsukawa.fasterner.personalinfo;

import android.content.Context;
import android.content.SharedPreferences;
import com.zstu.natsukawa.fasterner.base.DataManager;
import com.zstu.natsukawa.fasterner.base.Datas;
import io.reactivex.Observable;

public class PersonalInfoModel implements PersonalInfoContract.PersonalInfoModel {

    private DataManager dataManager;

    PersonalInfoModel(DataManager dataManager){
        this.dataManager = dataManager;
    }


    @Override
    public Observable<PersonalInfoBean> getPersonalInfoFromWeb(String studentNumber) {
        return dataManager.getPersonalInfo(studentNumber);
    }

    @Override
    public void refreshPersonalInfo(Context context, PersonalInfoBean personalInfoBean) {
        SharedPreferences sp = context.getSharedPreferences(Datas.SP_NAME,Context.MODE_PRIVATE);
        sp.edit()
                .putString("studentName",personalInfoBean.getStudentName())
                .putString("studentNumber",personalInfoBean.getStudentNumber())
                .putString("studentRoleInTeam",personalInfoBean.getStudentRoleInTeam())
                .putString("studentMemberLevel",personalInfoBean.getStudentMemberLevel())
                .putFloat("technology",personalInfoBean.getTechnologyLevel())
                .putFloat("characteristic",personalInfoBean.getCharacteristicLevel())
                .putFloat("otherAbility",personalInfoBean.getOtherAbilityLevel())
                .putFloat("professionalAbility",personalInfoBean.getProfessionalKnowledgeLevel())
                .putFloat("learningAbility",personalInfoBean.getLearningAbilityLevel())
                .apply();
    }

    @Override
    public PersonalInfoBean getStudentStatusFromLocal(Context context) {
        PersonalInfoBean personalInfoBean = new PersonalInfoBean();
        SharedPreferences sp = context.getSharedPreferences(Datas.SP_NAME, Context.MODE_PRIVATE);
        personalInfoBean.setStudentNumber(sp.getString("studentNumber", "null"));
        personalInfoBean.setStudentName(sp.getString("studentName", "null"));
        personalInfoBean.setStudentMemberLevel(sp.getString("studentMemberLevel", "unregistered"));
        personalInfoBean.setStudentRoleInTeam(sp.getString("studentRoleInTeam", "null"));
        personalInfoBean.setTechnologyLevel(sp.getFloat("technology",0f));
        personalInfoBean.setCharacteristicLevel(sp.getFloat("characteristic",0f));
        personalInfoBean.setProfessionalKnowledgeLevel(sp.getFloat("professionalAbility",0f));
        personalInfoBean.setLearningAbilityLevel(sp.getFloat("learningAbility",0f));
        personalInfoBean.setOtherAbilityLevel(sp.getFloat("otherAbility", 0f));
        return personalInfoBean;
    }
}
