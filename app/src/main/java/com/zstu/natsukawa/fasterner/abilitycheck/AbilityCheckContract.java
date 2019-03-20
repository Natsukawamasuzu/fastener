package com.zstu.natsukawa.fasterner.abilitycheck;

import com.zstu.natsukawa.fasterner.base.BaseModelInterface;
import com.zstu.natsukawa.fasterner.base.BasePresenter;
import com.zstu.natsukawa.fasterner.base.BaseViewInterface;
import io.reactivex.Observable;

import java.util.ArrayList;

interface AbilityCheckContract {
    interface AbilityCheckView extends BaseViewInterface{
        void showQuestions(ArrayList<QuestionBean> questionList);
    }
    interface AbilityCheckModel extends BaseModelInterface{
        Observable<ArrayList<QuestionBean>> getAbilityQuestionList(String studentNumber);
        Observable<ArrayList<QuestionBean>> getCharacQuestionList(String studentNumber);
    }
    abstract class AbilityCheckPresenter extends BasePresenter<AbilityCheckModel,AbilityCheckView>{
       abstract void getAbilityQuestionList(String studentNumber);
       abstract void getCharacQuestionList(String studentNumber);
    }

}
