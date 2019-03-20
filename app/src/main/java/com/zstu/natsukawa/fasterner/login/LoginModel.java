package com.zstu.natsukawa.fasterner.login;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.zstu.natsukawa.fasterner.base.DataManager;
import com.zstu.natsukawa.fasterner.base.Datas;
import io.reactivex.Observable;

import java.util.HashMap;
import java.util.Map;

public class LoginModel implements LoginContract.LoginModel {

    private DataManager dataManager;

    LoginModel(DataManager dataManager){
        this.dataManager = dataManager;
    }

    @Override
    public Observable<JSONObject> login(String studentNumber, String password) {
        Map<String, String> jsonObject = new HashMap<>();
        jsonObject.put("username",studentNumber);
        jsonObject.put("password",password);
        return dataManager.login(jsonObject);
    }

    @Override
    public void refreshToken(Context context, String tokenString) {
        SharedPreferences sp = context.getSharedPreferences(Datas.SP_NAME,Context.MODE_PRIVATE);
        sp.edit().putString("studentToken", tokenString).apply();
        Log.d("LoginModel", sp.getString("studentToken",null));
    }
}
