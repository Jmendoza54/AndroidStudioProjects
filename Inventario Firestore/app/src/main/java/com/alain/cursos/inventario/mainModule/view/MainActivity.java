package com.alain.cursos.inventario.mainModule.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.alain.cursos.inventario.R;
import com.alain.cursos.inventario.addModule.view.AddProductFragment;
import com.alain.cursos.inventario.common.pojo.Product;
import com.alain.cursos.inventario.detailModule.view.DetailFragment;
import com.alain.cursos.inventario.mainModule.MainPresenter;
import com.alain.cursos.inventario.mainModule.MainPresenterClass;
import com.alain.cursos.inventario.mainModule.view.adapters.OnItemClickListener;
import com.alain.cursos.inventario.mainModule.view.adapters.ProductAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public class MainActivity extends AppCompatActivity implements OnItemClickListener, MainView {


    private static final String FRAGMENT_DETAIL_PRODUCT = DetailFragment.class.getName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.contentMain)
    ConstraintLayout contentMain;
    /*@BindView(R.id.fab)
    FloatingActionButton fab;*/// FIXME: 10/09/2018 check why exist

    private MainPresenter mPresenter;
    private ProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configToolbar();
        configAdapter();
        configRecyclerView();

        mPresenter = new MainPresenterClass(this);
        mPresenter.onCreate();
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
    }

    private void configAdapter(){
        mAdapter = new ProductAdapter(new ArrayList<Product>(), this);
    }

    private void configRecyclerView(){
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.main_columns));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onAddClicked() {
        new AddProductFragment().show(getSupportFragmentManager(), getString(R.string.addProduct_title));
    }

    /*
     *   MainView
     * */

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void add(Product product) {
        mAdapter.add(product);
    }

    @Override
    public void update(Product product) {
        mAdapter.update(product);
    }

    @Override
    public void remove(Product product) {
        mAdapter.remove(product);
    }

    @Override
    public void removeFail() {
        Snackbar.make(contentMain, R.string.main_error_remove, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onShowError(int resMsg) {
        Snackbar.make(contentMain, resMsg, Snackbar.LENGTH_LONG).show();
    }

    /*
     *   OnItemClickListener
     * */

    @Override
    public void onItemClick(Product product) {
        Bundle arguments = new Bundle();
        arguments.putString(Product.ID, product.getId());
        arguments.putString(Product.NAME, product.getName());
        arguments.putInt(Product.QUANTITY, product.getQuantity());
        arguments.putString(Product.PHOTO_URL, product.getPhotoUrl());
        arguments.putDouble(Product.SCORE, product.getScore());
        arguments.putLong(Product.TOTAL_VOTES, product.getTotalVotes());

        getSupportFragmentManager().beginTransaction().add(R.id.contentMain,
                DetailFragment.instantiate(this, FRAGMENT_DETAIL_PRODUCT, arguments),
                FRAGMENT_DETAIL_PRODUCT).addToBackStack(null).commit();
    }

    @Override
    public void onLongItemClick(final Product product) {
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null){
            vibrator.vibrate(60);
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.main_dialog_remove_title)
                .setMessage(R.string.main_dialog_remove_message)
                .setPositiveButton(R.string.main_dialog_remove_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.remove(product);
                    }
                })
                .setNegativeButton(R.string.common_dialog_cancel, null)
                .show();
    }
}
