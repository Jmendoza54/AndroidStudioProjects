package com.alain.cursos.inventario.mainModule;

import com.alain.cursos.inventario.common.pojo.Product;
import com.alain.cursos.inventario.mainModule.events.MainEvent;
import com.alain.cursos.inventario.mainModule.model.MainInteractor;
import com.alain.cursos.inventario.mainModule.model.MainInteractorClass;
import com.alain.cursos.inventario.mainModule.view.MainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/* *
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public class MainPresenterClass implements MainPresenter {
    private MainView mView;
    private MainInteractor mInteractor;

    public MainPresenterClass(MainView mView) {
        this.mView = mView;
        this.mInteractor = new MainInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        mInteractor.unsubscribeToProducts();
    }

    @Override
    public void onResume() {
        mInteractor.subscribeToProducts();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void remove(Product product) {
        if (setProgress()){
            mInteractor.removeProduct(product);
        }
    }

    private boolean setProgress() {
        if (mView != null){
            mView.showProgress();
            return true;
        }
        return false;
    }

    @Subscribe
    @Override
    public void onEventListener(MainEvent event) {
        if (mView != null){
            mView.hideProgress();

            switch (event.getTypeEvent()){
                case MainEvent.SUCCESS_ADD:
                    mView.add(event.getProduct());
                    break;
                case MainEvent.SUCCESS_UPDATE:
                    mView.update(event.getProduct());
                    break;
                case MainEvent.SUCCESS_REMOVE:
                    mView.remove(event.getProduct());
                    break;
                case MainEvent.ERROR_SERVER:
                    mView.onShowError(event.getResMsg());
                    break;
                case MainEvent.ERROR_TO_REMOVE:
                    mView.removeFail();
                    break;
            }
        }
    }
}
