package com.marceljurtz.lifecounter.views.About;

import com.marceljurtz.lifecounter.contracts.base.IView;

public interface IAboutView extends IView {
    void loadActivity(Class c);
    void loadAboutPage(String url);
}
