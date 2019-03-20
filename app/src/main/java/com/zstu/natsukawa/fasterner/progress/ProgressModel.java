package com.zstu.natsukawa.fasterner.progress;

import com.zstu.natsukawa.fasterner.base.DataManager;
import io.reactivex.Observable;

import java.util.ArrayList;

public class ProgressModel implements ProgressContract.ProgressModel {
    DataManager dataManager;
    ProgressModel(DataManager dataManager){
        this.dataManager = dataManager;
    }

    public Observable<ArrayList<ProgressBean>> getTeamProgress(String studentNumber){
        return dataManager.getTeamProgress(studentNumber);
    }
}
