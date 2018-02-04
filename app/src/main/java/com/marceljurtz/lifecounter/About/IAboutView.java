package com.marceljurtz.lifecounter.About;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;

public interface IAboutView extends IView {
    void loadActivity(Class c);
    void loadAboutPage(String url);
}
