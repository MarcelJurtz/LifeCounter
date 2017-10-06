package com.marceljurtz.lifecounter.Dicing;

public class DicingPresenter implements IDicingPresenter {

    private DicingModel model;
    private IDicingView view;

    public DicingPresenter(IDicingView view) {
        this.view = view;
    }

    //region Activity Lifecycle

    @Override
    public void onCreate() {
        model = new DicingModel();
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

    @Override
    public void onScreenTap() {
        view.setDicingText(model.ThrowDice()+"");
    }


    //region NavDrawer Handling

    @Override
    public void onMenuEntrySettingsTap() {
        view.startSettingsActivity();
    }

    @Override
    public void onMenuEntryAboutTap() {
        view.startAboutActivity();
    }

    @Override
    public void onMenuEntryTwoPlayerTap() {
        // TODO Set amount of players
        view.start2PlayerGame();
    }

    @Override
    public void onMenuEntryFourPlayerTap() {
        // TODO Set amount of players
        view.start4PlayerGame();
    }

    //endregion
}
