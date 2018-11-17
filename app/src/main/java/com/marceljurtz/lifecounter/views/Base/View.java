package com.marceljurtz.lifecounter.views.Base;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.marceljurtz.lifecounter.R;
import com.marceljurtz.lifecounter.contracts.base.IPresenter;
import com.marceljurtz.lifecounter.contracts.base.IView;

import androidx.appcompat.app.AppCompatActivity;

public class View extends AppCompatActivity implements IView {

    protected IPresenter _presenter;

    private NavigationView _navigationView;
    private Menu _navMenu;

    public void loadActivity(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        if(_presenter != null)
            _presenter.onDestroy();

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if(_presenter != null)
            _presenter.onPause();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(_presenter != null)
            _presenter.onResume();
    }

    public void disableMenuItem(int navDrawerId, int menuItemId) {
        if(_navigationView == null) {
            _navigationView = (NavigationView) findViewById(navDrawerId);
            _navMenu = _navigationView.getMenu();
        }

        _navMenu.findItem(menuItemId).setVisible(false);
    }

    public void disableMenuItem(NavigationView view, int menuItemId) {
        if(_navigationView == null) {
            _navigationView = view;
            _navMenu = _navigationView.getMenu();
        }

        _navMenu.findItem(menuItemId).setVisible(false);
    }
}
