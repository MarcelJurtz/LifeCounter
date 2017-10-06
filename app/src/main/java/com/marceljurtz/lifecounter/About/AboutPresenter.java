package com.marceljurtz.lifecounter.About;

public class AboutPresenter implements IAboutPresenter {

    String languageCode;
    IAboutView view;

    public AboutPresenter(IAboutView view, String languageCode) {
        this.view = view;
        this.languageCode = languageCode;
    }

    @Override
    public void onCreate() {
        if (languageCode.equals("Deutsch")) {
            view.loadAboutPage("file:///android_asset/about_de.html");
        } else {
            view.loadAboutPage("file:///android_asset/about_en.html");
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}
