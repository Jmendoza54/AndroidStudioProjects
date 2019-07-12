package com.alain.cursos.inventario.addModule;

import com.alain.cursos.inventario.addModule.events.AddProductEvent;
import com.alain.cursos.inventario.addModule.model.AddProductInteractor;
import com.alain.cursos.inventario.addModule.model.AddProductInteractorClass;
import com.alain.cursos.inventario.addModule.view.AddProductView;
import com.alain.cursos.inventario.common.pojo.Product;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public class AddProductPresenterClass implements AddProductPresenter {

    private AddProductView mView;
    private AddProductInteractor mInteractor;

    public AddProductPresenterClass(AddProductView mView) {
        this.mView = mView;
        this.mInteractor = new AddProductInteractorClass();
    }

    @Override
    public void onShow() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void addProduct(Product product) {
        if (setProgress()){
            mInteractor.addProduct(product);
        }
    }

    private boolean setProgress() {
        if (mView != null){
            mView.disableUIElements();
            mView.showProgress();
            return true;
        }
        return false;
    }

    @Subscribe
    @Override
    public void onEventListener(AddProductEvent event) {
        if (mView != null){
            mView.hideProgress();
            mView.enableUIElements();

            switch (event.getTypeEvent()){
                case AddProductEvent.SUCCESS_ADD:
                    mView.productAdded();
                    break;
                case AddProductEvent.ERROR_MAX_VALUE:
                    mView.maxValueError(event.getResMsg());
                    break;
                case AddProductEvent.ERROR_SERVER:
                    mView.showError(event.getResMsg());
                    break;
            }
        }
    }
}
