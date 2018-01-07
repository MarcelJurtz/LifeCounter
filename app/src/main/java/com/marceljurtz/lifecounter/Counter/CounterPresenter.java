package com.marceljurtz.lifecounter.Counter;

import android.content.SharedPreferences;
import android.widget.LinearLayout;

import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;

import java.util.ArrayList;

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
        PreferenceManager.SavePlayerData(preferences, players);
    }

    @Override
    public void OnResume() {
        // Load items from preferences
        players = PreferenceManager.LoadPlayerData(preferences);

        // Reload View
        view.DeleteAllCounters();

        for (Player player : players) {

            view.SetPlayerIdentificationText(player.getPlayerID(), player.GetPlayerIdentification());

            for (Counter counter : player.GetAllCounters()) {
                view.AddCounterToPlayer(player.getPlayerID(), counter);
            }
        }
    }

    @Override
    public void OnDestroy() {

    }

    @Override
    public void OnCreateNewCounterButtonTap() {
        view.LoadCounterAddDialog(players);
    }

    @Override
    public void AddCounterToPlayer(PlayerID playerId, Counter counter) {
        for (Player player : players) {
            if (player.getPlayerID().equals(playerId)) {
                counter.SetIdentifier(player.getPlayerID().toString() + "-" + player.GetAllCounters().size());
                player.AddCounter(counter);
                break;
            }
        }

        view.AddCounterToPlayer(playerId, counter);
    }

    @Override
    public void OnPlayerIdentificationChanged(PlayerID playerId, String newIdentification) {

        for (Player player : players) {
            if (player.getPlayerID() == playerId) {
                player.SetPlayerIdentification(newIdentification);
                break;
            }
        }

        view.SetPlayerIdentificationText(playerId, newIdentification);
    }

    @Override
    public void OnMenuEntryTwoPlayerClick() {
        PreferenceManager.saveDefaultPlayerAmount(preferences, 2);
        view.LoadGameActivity();
    }

    @Override
    public void OnMenuEntryFourPlayerClick() {
        PreferenceManager.saveDefaultPlayerAmount(preferences, 4);
        view.LoadGameActivity();
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

    @Override
    public String GetPlayerIdentification(PlayerID playerID) {
        for (Player player : players) {
            if (player.getPlayerID() == playerID) {
                return player.GetPlayerIdentification();
            }
        }
        return "";
    }

    @Override
    public void OnPlayerIdentificationTap(PlayerID playerID) {
        view.LoadPlayerIdentificationDialog(playerID, GetPlayerIdentification(playerID));
    }

    @Override
    public void OnPlayerIdentificationLongTap(PlayerID playerID) {
        view.LoadMultipleCounterDeletionDialog(playerID);
    }

    @Override
    public void OnPlayerDeletionConfirmation(PlayerID playerID) {
        for(Player player : players) {
            if(player.getPlayerID() == playerID) {
                player.ClearCounters();
                player.SetPlayerIdentification("");
                break;
            }
        }
        view.DeleteAllCountersForPlayer(playerID);
    }

    @Override
    public void OnCounterTap(String counterDescription, LinearLayout counterLayout) {

    }

    @Override
    public void OnCounterLongTap(LinearLayout counterLayout) {
        view.LoadCounterDeletionDialog(counterLayout);
    }

    @Override
    public void OnCounterDeletionConfirmation(LinearLayout counterLayout) {
        boolean deleteParent = false;

        Player currentPlayer;

        String tag = (String) counterLayout.getTag();
        switch (PlayerID.valueOf((tag.split("_"))[0])) {
            case ONE:
                currentPlayer = players.get(0);
                break;
            case TWO:
                currentPlayer = players.get(1);
                break;
            case THREE:
                currentPlayer = players.get(2);
                break;
            case FOUR:
                currentPlayer = players.get(3);
                break;
            default:
                currentPlayer = null;
        }

        if (currentPlayer != null) {
            for (Counter c : currentPlayer.GetAllCounters()) {
                if (c.GetIdentifier() == tag) {
                    currentPlayer.RemoveCounter(c);
                    break;
                }
            }
            if (currentPlayer.GetAllCounters().size() <= 0) {
                deleteParent = true;
            }

            view.DeleteCounter(counterLayout, deleteParent);
        }
    }
}
