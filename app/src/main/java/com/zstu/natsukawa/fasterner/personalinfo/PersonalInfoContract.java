package com.zstu.natsukawa.fasterner.personalinfo;

import android.content.Context;
import com.zstu.natsukawa.fasterner.base.BaseModelInterface;
import com.zstu.natsukawa.fasterner.base.BasePresenter;
import com.zstu.natsukawa.fasterner.base.BaseViewInterface;
import io.reactivex.Observable;

public interface PersonalInfoContract {
    interface PersonalInfoView extends BaseViewInterface{
        void showSuccess(String result);
        void showError();
    }
    interface PersonalInfoModel extends BaseModelInterface{
        Observable<PersonalInfoBean> getPersonalInfoFromWeb(String studentNumber);
        void refreshPersonalInfo(Context context, PersonalInfoBean personalInfoBean);
        PersonalInfoBean getStudentStatusFromLocal(Context context);
    }
    abstract class PersonalInfoPresenter extends BasePresenter<PersonalInfoModel, PersonalInfoView>{
        abstract void refreshStudentStatus(String studentNumber);
        abstract PersonalInfoBean getStudentStatusFromLocal();
    }

}
