package com.alain.cursos.inventario.detailModule;

/* *
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

import com.alain.cursos.inventario.common.pojo.Product;
import com.alain.cursos.inventario.detailModule.events.DetailProductEvent;
import com.alain.cursos.inventario.detailModule.model.DetailProductInteractor;
import com.alain.cursos.inventario.detailModule.model.DetailProductInteractorClass;
import com.alain.cursos.inventario.detailModule.view.DetailProductView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DetailProductPresenterClass implements DetailProductPresenter {
    private DetailProductInteractor mInteractor;
    private DetailProductView mView;

    public DetailProductPresenterClass(DetailProductView mView) {
        this.mView = mView;
        this.mInteractor = new DetailProductInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
        if (mView != null){
            mView.hideFab();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (mView != null){
            mView.showFab();
            mView = null;
        }
    }

    @Override
    public void updateProduct(Product product) {
        if (setProgress()){
            mInteractor.updateProduct(product);
        }
    }

    private boolean setProgress() {
        if (mView != null){
            mView.showProgress();
            mView.disableUIElements();
            return true;
        }
        return false;
    }

    @Subscribe
    @Override
    public void onEventListenr(DetailProductEvent event) {
        if (mView != null){
            mView.hideProgress();
            mView.enableUIElements();

            switch (event.getTypeEvent()){
                case DetailProductEvent.UPDATE_SUCCESS:
                    mView.updateSuccess();
                    break;
                case DetailProductEvent.ERROR_SERVER:
                    mView.updateError();
                    break;
            }
        }
    }
}
