package com.zstu.natsukawa.fasterner.base;

import android.content.Context;
import android.location.Location;
import com.alibaba.fastjson.JSONObject;
import com.zstu.natsukawa.fasterner.abilitycheck.QuestionBean;
import com.zstu.natsukawa.fasterner.msgmangement.MessageEntity;
import com.zstu.natsukawa.fasterner.personalinfo.PersonalInfoBean;
import com.zstu.natsukawa.fasterner.progress.ProgressBean;
import com.zstu.natsukawa.fasterner.register.RegisterBean;
import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class DataManager {

    private APIService apiService;
    public DataManager (Context context, String baseUrl){
        apiService = MyRetrofit.getInstance(context, baseUrl).getAPIService();
    }

    public Observable<JSONObject> login(Map<String,String> requestJsonString){
        return apiService.login(requestJsonString);
    }

    public Observable<RegisterBean> register(@NotNull RegisterBean registerBean){
        return apiService.register(registerBean);
    }

    public Observable<ArrayList<MessageEntity>> getMessages(@NotNull String studentNumber){
        return apiService.getMessage(studentNumber);
    }

    public Observable<PersonalInfoBean> getPersonalInfo(@NotNull String studentNumber){
        return apiService.getPersonalInfo(studentNumber);
    }

    public Observable<ArrayList<QuestionBean>> getAbilityQuestionList(@NotNull String studentNumber){
        return apiService.getAbilityTestQuestionList(studentNumber);
    }

    public Observable<ArrayList<QuestionBean>> getCharacteristicQuestionList(@NotNull String studentNumber){
        return apiService.getCharacteristicQuestionList(studentNumber);
    }

    public Observable<ArrayList<ProgressBean>> getTeamProgress(@NotNull String studentNumber){
        return apiService.getTeamProgress(studentNumber);
    }

    public Observable<JSONObject> attendance (@NotNull String studentNumber, Location location){
        return apiService.postAttendanceInfo(studentNumber,location.getLatitude(),location.getLongitude());
    }
}
