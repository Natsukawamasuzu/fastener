package com.zstu.natsukawa.fasterner.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private BasePresenter basePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(null != basePresenter)
            basePresenter.cancelRequest();
    }

    @Override
    protected void onDestroy() {
        if(null != basePresenter) {
            basePresenter.detachModel();
            basePresenter.detachView();
            basePresenter.destoryCD();
            basePresenter = null;
        }
        MyRetrofit.destroyMyRetrofit();
        super.onDestroy();
    }

    protected void linkChildPresenter(BasePresenter basePresenter){
        this.basePresenter = basePresenter;
    }
}
