package com.marceljurtz.lifecounter.Counter;

import android.content.SharedPreferences;
import android.widget.LinearLayout;

import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;

import java.util.ArrayList;
import java.util.Random;

public class CounterPresenter implements ICounterPresenter {

    private ArrayList<Player> players;
    private ICounterView view;
    private SharedPreferences preferences;
    private Random random;

    public CounterPresenter(ICounterView view, SharedPreferences preferences, ArrayList<Player> players) {
        this.players = players;
        this.preferences = preferences;
        this.view = view;
        random = new Random();
    }

    @Override
    public void onCreate() {
        int num = random.nextInt(4);
        Color color = new Color(MagicColor.WHITE, preferences);
        switch(num) {
            case 0:
                color = new Color(MagicColor.BLUE, preferences);
                break;
            case 1:
                color = new Color(MagicColor.GREEN, preferences);
                break;
            case 2:
                color = new Color(MagicColor.RED, preferences);
                break;
            case 3:
                break;
        }
        view.setBackgroundColor(color);
    }

    @Override
    public void onPause() {
        PreferenceManager.savePlayerCounterData(preferences, players);
    }

    @Override
    public void onResume() {
        // Load items from preferences
        players = PreferenceManager.loadPlayerCounterData(preferences);

        // Reload View
        view.deleteAllCounters();

        for (Player player : players) {

            view.setPlayerIdentificationText(player.getPlayerID(), player.getPlayerIdentification());

            for (Counter counter : player.getAllCounters()) {
                view.addCounter(player.getPlayerID(), counter);
            }
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onFloatingActionButtonTap() {
        view.loadCounterAddDialog(players);
    }

    @Override
    public void addCounter(PlayerID playerId, Counter counter) {
        for (Player player : players) {
            if (player.getPlayerID().equals(playerId)) {
                counter.setIdentifier(player.getPlayerID().toString() + "-" + player.getAllCounters().size());
                player.addCounter(counter);
                break;
            }
        }

        view.addCounter(playerId, counter);
    }

    @Override
    public void onPlayerIdentificationChangeConfirmed(PlayerID playerId, String newIdentification) {

        for (Player player : players) {
            if (player.getPlayerID() == playerId) {
                player.setPlayerIdentification(newIdentification);
                break;
            }
        }

        view.setPlayerIdentificationText(playerId, newIdentification);
    }

    @Override
    public void onMenuEntryTwoPlayerClick() {
        PreferenceManager.saveDefaultPlayerAmount(preferences, 2);
        view.loadGameActivity();
    }

    @Override
    public void onMenuEntryFourPlayerClick() {
        PreferenceManager.saveDefaultPlayerAmount(preferences, 4);
        view.loadGameActivity();
    }

    @Override
    public void onMenuEntryDicingClick() {
        view.loadDicingActivity();
    }

    @Override
    public void onMenuEntrySettingsClick() {
        view.loadSettingsActivity();
    }

    @Override
    public void onMenuEntryAboutClick() {
        view.loadAboutActivity();
    }

    @Override
    public String getPlayerIdentification(PlayerID playerID) {
        for (Player player : players) {
            if (player.getPlayerID() == playerID) {
                return player.getPlayerIdentification();
            }
        }
        return "";
    }

    @Override
    public void onPlayerIdentificationTap(PlayerID playerID) {
        view.loadPlayerIdentificationDialog(playerID, getPlayerIdentification(playerID));
    }

    @Override
    public void onPlayerIdentificationLongTap(PlayerID playerID) {
        view.loadPlayerDeletionDialog(playerID);
    }

    @Override
    public void onPlayerDeletionConfirmed(PlayerID playerID) {
        for (Player player : players) {
            if (player.getPlayerID() == playerID) {
                player.clearCounters();
                player.setPlayerIdentification("");
                break;
            }
        }
        view.deleteAllCountersForPlayer(playerID);
    }

    @Override
    public void onCounterDeletionConfirmed(LinearLayout counterLayout) {
        boolean deleteParent = false;

        Counter currentCounter = getCounterByIdentifier(counterLayout.getTag().toString());
        Player currentPlayer = getPlayerByCounterIdentifier(counterLayout.getTag().toString());

        if (currentPlayer != null && currentCounter != null) {
            currentPlayer.removeCounter(currentCounter);
            if (currentPlayer.getAllCounters().size() <= 0) {
                deleteParent = true;
            }
        }

        view.deleteCounter(counterLayout, deleteParent);
    }

    @Override
    public void onCounterTap(String identifier) {
        view.loadCounterEditDialog(getPlayerByCounterIdentifier(identifier), getCounterByIdentifier(identifier));
    }

    @Override
    public void onCounterLongTap(LinearLayout counterLayout) {
        view.loadCounterDeletionDialog(counterLayout);
    }

    @Override
    public void onCounterEditCompleted(PlayerID playerID, String oldCounterIdentifier, Counter newCounter) {
        newCounter.setIdentifier(oldCounterIdentifier);
        switch (playerID) {
            case ONE:
                players.get(0).updateCounter(newCounter);
                view.updateCounterView(players.get(0), newCounter);
                break;
            case TWO:
                players.get(1).updateCounter(newCounter);
                view.updateCounterView(players.get(1), newCounter);
                break;
            case THREE:
                players.get(2).updateCounter(newCounter);
                view.updateCounterView(players.get(2), newCounter);
                break;
            case FOUR:
                players.get(3).updateCounter(newCounter);
                view.updateCounterView(players.get(3), newCounter);
                break;
            default:
                break;
        }
    }

    private Counter getCounterByIdentifier(String identifier) {
        Player currentPlayer;

        switch (PlayerID.valueOf((identifier.split("_"))[0])) {
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
            for (Counter c : currentPlayer.getAllCounters()) {
                if (c.getIdentifier() == identifier) {
                    return c;
                }
            }
        }

        return null;
    }

    private Player getPlayerByCounterIdentifier(String identifier) {
        switch (PlayerID.valueOf((identifier.split("_"))[0])) {
            case ONE:
                return players.get(0);
            case TWO:
                return players.get(1);
            case THREE:
                return players.get(2);
            case FOUR:
                return players.get(3);
            default:
                return null;
        }
    }
}
