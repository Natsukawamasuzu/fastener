package com.zstu.natsukawa.fasterner.abilitycheck;

import com.zstu.natsukawa.fasterner.base.DataManager;
import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class AbilityCheckModel implements AbilityCheckContract.AbilityCheckModel {
    private DataManager dataManager;

    AbilityCheckModel(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public Observable<ArrayList<QuestionBean>> getAbilityQuestionList(@NotNull String studentNumber){
        return dataManager.getAbilityQuestionList(studentNumber);
    }

    @Override
    public Observable<ArrayList<QuestionBean>> getCharacQuestionList(String studentNumber) {
        return dataManager.getCharacteristicQuestionList(studentNumber);
    }
}
