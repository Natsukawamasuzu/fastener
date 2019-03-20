package com.zstu.natsukawa.fasterner.base;

import com.alibaba.fastjson.JSONObject;
import com.zstu.natsukawa.fasterner.abilitycheck.QuestionBean;
import com.zstu.natsukawa.fasterner.msgmangement.MessageEntity;
import com.zstu.natsukawa.fasterner.personalinfo.PersonalInfoBean;
import com.zstu.natsukawa.fasterner.progress.ProgressBean;
import com.zstu.natsukawa.fasterner.register.RegisterBean;
import io.reactivex.Observable;

import retrofit2.http.*;

import java.util.ArrayList;
import java.util.Map;


public interface APIService {

    @POST("user/login")
    Observable<JSONObject> login (@Body Map<String,String> requestJsonString);

    @POST("register")
    Observable<RegisterBean> register(@Body RegisterBean registerBean);

    @GET("{STUDENT_NUMBER}/messages")
    Observable<ArrayList<MessageEntity>> getMessage(@Path("STUDENT_NUMBER") String studentNumber);

    @GET("{STUDENT_NUMBER}/personal_info")
    Observable<PersonalInfoBean> getPersonalInfo(@Path("STUDENT_NUMBER") String studentNumber);

    @GET("{STUDENT_NUMBER}/ability_test/question_list")
    Observable<ArrayList<QuestionBean>> getAbilityTestQuestionList(@Path("STUDENT_NUMBER") String studentNumber);

    @GET("{STUDENT_NUMBER}/characteristic_test/question_list")
    Observable<ArrayList<QuestionBean>> getCharacteristicQuestionList(@Path("STUDENT_NUMBER") String studentNumber);

    @GET("{STUDENT_NUMBER}/progress")
    Observable<ArrayList<ProgressBean>> getTeamProgress(@Path("STUDENT_NUMBER") String studentNumber);

    @FormUrlEncoded
    @POST("{STUDENT_NUMBER}/attendance")
    Observable<JSONObject> postAttendanceInfo(@Path("STUDENT_NUMBER") String studentNumber,
                                              @Field("latitude") double latitude,
                                              @Field("longitude") double longitude);
}
