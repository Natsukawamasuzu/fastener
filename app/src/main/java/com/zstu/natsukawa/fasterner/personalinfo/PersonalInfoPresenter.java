package com.zstu.natsukawa.fasterner.personalinfo;

import android.util.Log;
import androidx.fragment.app.Fragment;
import com.zstu.natsukawa.fasterner.base.DataManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

class PersonalInfoPresenter extends PersonalInfoContract.PersonalInfoPresenter {

    private PersonalInfoBean personalInfo;
    private PersonalInfoFragment personalInfoFragment;

    PersonalInfoPresenter(Fragment fragment, String url) {
        attachModel(new PersonalInfoModel(new DataManager(fragment.getContext(), url)));
        attachToView((PersonalInfoContract.PersonalInfoView) fragment);
        personalInfoFragment = (PersonalInfoFragment) fragment;
    }

    @Override
    void refreshStudentStatus(String studentNumber) {
        getCompositeDisposable().add(getModel().getPersonalInfoFromWeb(studentNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PersonalInfoBean>() {
                    @Override
                    public void onNext(PersonalInfoBean personalInfoBean) {
                        personalInfo = personalInfoBean;
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError();
                    }

                    @Override
                    public void onComplete() {
                        getModel().refreshPersonalInfo(personalInfoFragment.getContext(),personalInfo);
                        getView().showSuccess(personalInfo.getAvatarUrl());
                    }
                }));
    }

    @Override
    PersonalInfoBean getStudentStatusFromLocal() {
        return getModel().getStudentStatusFromLocal(personalInfoFragment.getContext());
    }

}
