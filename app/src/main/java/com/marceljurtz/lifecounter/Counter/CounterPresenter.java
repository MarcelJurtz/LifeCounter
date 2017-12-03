package com.marceljurtz.lifecounter.Counter;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import static com.marceljurtz.lifecounter.Helper.PlayerID.ONE;

public class CounterPresenter implements ICounterPresenter {

    private ArrayList<Player> players;
    private ICounterView view;
    private SharedPreferences preferences;

    public CounterPresenter(ICounterView view, SharedPreferences preferences, ArrayList<Player> players) {
        this.players = players;
        this.preferences = preferences;
        this.view = view;
    }

    @Override
    public void OnCreate() {

    }

    @Override
    public void OnPause() {
        // Save items to preferences
        PreferenceManager.SavePlayerData(preferences, players);
    }

    @Override
    public void OnResume() {
        // Load items from preferences
        players = PreferenceManager.LoadPlayerData(preferences);

        // Reload View
        view.DeleteAllCounters();

        for(Player player : players) {

            view.SetPlayerIdentificationText(player.getPlayerID(), player.getPlayerIdentification());

            for(Counter counter : player.GetAllCounters()) {
                view.DisplayNewCounterEntryToPlayer(player.getPlayerID(), counter);
            }
        }
    }

    @Override
    public void OnDestroy() {

    }

    @Override
    public void OnCreateNewCounterButtonTap() {
        view.LoadCounterAddDialog();
    }

    @Override
    public void AddCounterToPlayer(PlayerID playerId, Counter counter) {
        for(Player player : players) {
           if(player.getPlayerID().equals(playerId)) {
               player.AddCounter(counter);
               break;
           }
        }

        view.DisplayNewCounterEntryToPlayer(playerId, counter);
    }

    @Override
    public void OnPlayerIdentificationChanged(PlayerID playerId, String newIdentification) {

        for(Player player : players) {
            if(player.getPlayerID() == playerId) {
                player.setPlayerIdentification(newIdentification);
                break;
            }
        }

        view.SetPlayerIdentificationText(playerId, newIdentification);
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
