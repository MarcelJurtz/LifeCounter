package com.marceljurtz.lifecounter.Counter;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;

import java.util.List;

public class CounterPresenter implements ICounterPresenter {

    private List<Player> players;
    private ICounterView view;
    private SharedPreferences preferences;

    public CounterPresenter(ICounterView view, SharedPreferences preferences, List<Player> players) {
        this.players = players;
        this.preferences = preferences;
        this.view = view;
    }

    @Override
    public void OnCreate() {

    }

    @Override
    public void OnPause() {

    }

    @Override
    public void OnResume() {

    }

    @Override
    public void OnDestroy() {

    }

    @Override
    public void OnCreateNewCounterButtonTap() {
        view.LoadCounterAddDialog();
    }

    @Override
    public void AddCounterToPlayer(Player player, Counter counter) {
        player.AddCounter(counter);
        view.DisplayNewCounterEntryToPlayer(player, counter);
    }

    @Override
    public void OnPlayerIdentificationChanged(Player player, String newIdentification) {
        player.setPlayerIdentification(newIdentification);
        view.SetPlayerIdentificationText(player, newIdentification);
    }

    @Override
    public void OnMenuEntryTwoPlayerClick() {
        if(players.size() == 4) {
            PreferenceManager.saveDefaultPlayerAmount(preferences, 2);
            view.LoadGameActivity();
        }
        else view.GoBackToPreviousActivity();
    }

    @Override
    public void OnMenuEntryFourPlayerClick() {
        if(players.size() == 2) {
            PreferenceManager.saveDefaultPlayerAmount(preferences, 4);
            view.LoadGameActivity();
        }
        else view.GoBackToPreviousActivity();
    }

    @Override
    public void OnMenuEntryDicingClick() {
        view.LoadDicingActivity();
    }

    @Override
    public void OnMenuEntrySettingsClick() {
        view.LoadSettingsActivity();
    }

    @Override
    public void OnMenuEntryAboutClick() {
        view.LoadAboutActivity();
    }
}
