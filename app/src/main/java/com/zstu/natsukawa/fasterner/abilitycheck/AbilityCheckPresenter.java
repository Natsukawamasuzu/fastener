package com.zstu.natsukawa.fasterner.abilitycheck;

import androidx.fragment.app.Fragment;
import com.zstu.natsukawa.fasterner.base.DataManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;

public class AbilityCheckPresenter extends AbilityCheckContract.AbilityCheckPresenter {

    ArrayList<QuestionBean> questionList = new ArrayList<>();
    private AbilityCheckFragment abilityCheckFragment;

    public AbilityCheckPresenter(Fragment fragment, String url) {
        attachModel(new AbilityCheckModel(new DataManager(fragment.getContext(), url)));
        attachToView((AbilityCheckContract.AbilityCheckView) fragment);
        abilityCheckFragment = (AbilityCheckFragment) fragment;
    }

    @Override
    public void getAbilityQuestionList(String studentNumber) {
        getCompositeDisposable().add(getModel().getAbilityQuestionList(studentNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<QuestionBean>>() {
                    @Override
                    public void onNext(ArrayList<QuestionBean> questionBeans) {
                        questionList = questionBeans;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                       getView().showQuestions(questionList);
                    }
                }));
    }

    @Override
    void getCharacQuestionList(String studentNumber) {
        getCompositeDisposable().add(getModel().getCharacQuestionList(studentNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<QuestionBean>>() {
                    @Override
                    public void onNext(ArrayList<QuestionBean> questionBeans) {
                        questionList = questionBeans;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

}
