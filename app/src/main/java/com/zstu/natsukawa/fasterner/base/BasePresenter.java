package com.zstu.natsukawa.fasterner.base;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter <M extends BaseModelInterface, V extends BaseViewInterface>{

    private M model;
    private V view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void attachToView(V view){
        this.view = view;
    }

    protected void attachModel(M model){
        this.model = model;
    }

    protected M getModel(){
        return model;
    }

    protected V getView(){
        return view;
    }

    protected CompositeDisposable getCompositeDisposable(){
        return compositeDisposable;
    }

    protected void detachView(){
        view = null;
    }

    protected void detachModel(){
        model = null;
    }

    protected void cancelRequest(){
        compositeDisposable.clear();
    }

    protected void destoryCD(){
        if(!compositeDisposable.isDisposed())
            compositeDisposable.dispose();
        compositeDisposable = null;
    }

}
