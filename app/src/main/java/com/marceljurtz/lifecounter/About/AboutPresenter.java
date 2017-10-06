package com.marceljurtz.lifecounter.About;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IAboutNavDrawerInteraction;

public class AboutPresenter implements IAboutPresenter {

    String languageCode;
    IAboutView view;

    public AboutPresenter(IAboutView view, String languageCode) {
        this.view = view;
        this.languageCode = languageCode;
    }

    //region Activity Lifecycle

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

    //endregion

    //region NavDrawer Handling

    @Override
    public void onMenuEntryTwoPlayerTap() {
        view.start2PlayerGame();
    }

    @Override
    public void onMenuEntryFourPlayerTap() {
        view.start4PlayerGame();
    }

    @Override
    public void onMenuEntryDicingTap() {
        view.startDicingActivity();
    }

    @Override
    public void onMenuEntrySettingsTap() {
        view.startSettingsActivity();
    }

    //endregion
}
