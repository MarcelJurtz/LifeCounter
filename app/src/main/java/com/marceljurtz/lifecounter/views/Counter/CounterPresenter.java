package com.marceljurtz.lifecounter.views.Counter;

import android.content.SharedPreferences;
import android.widget.LinearLayout;

import com.marceljurtz.lifecounter.views.About.AboutActivity;
import com.marceljurtz.lifecounter.views.Base.Presenter;
import com.marceljurtz.lifecounter.views.Dicing.DicingActivity;
import com.marceljurtz.lifecounter.views.Game.GameActivity;
import com.marceljurtz.lifecounter.models.Color;
import com.marceljurtz.lifecounter.models.Counter;
import com.marceljurtz.lifecounter.enums.MagicColorEnum;
import com.marceljurtz.lifecounter.models.Player;
import com.marceljurtz.lifecounter.enums.PlayerIdEnum;
import com.marceljurtz.lifecounter.models.PreferenceManager;
import com.marceljurtz.lifecounter.views.Settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Random;

public class CounterPresenter extends Presenter implements ICounterPresenter {

    private ArrayList<Player> players;
    private Random _random;

    public CounterPresenter(ICounterView view, SharedPreferences preferences, ArrayList<Player> players) {
        super(view, preferences);
        this.players = players;
        _view = view;
        _random = new Random();
    }

    @Override
    public void onCreate() {
        int num = _random.nextInt(4);
        Color color = new Color(MagicColorEnum.WHITE, _preferences);
        switch(num) {
            case 0:
                color = new Color(MagicColorEnum.BLUE, _preferences);
                break;
            case 1:
                color = new Color(MagicColorEnum.GREEN, _preferences);
                break;
            case 2:
                color = new Color(MagicColorEnum.RED, _preferences);
                break;
            case 3:
                break;
        }
        ((ICounterView)_view).setBackgroundColor(color);
    }

    @Override
    public void onPause() {
        PreferenceManager.savePlayerCounterData(_preferences, players);
    }

    @Override
    public void onResume() {
        // Load items from preferences
        players = PreferenceManager.loadPlayerCounterData(_preferences);

        // Reload View
        ((ICounterView)_view).deleteAllCounters();

        for (Player player : players) {

            ((ICounterView)_view).setPlayerIdentificationText(player.getPlayerIdEnum(), player.getPlayerIdentification());

            for (Counter counter : player.getAllCounters()) {
                ((ICounterView)_view).addCounter(player.getPlayerIdEnum(), counter);
            }
        }
    }

    @Override
    public void onFloatingActionButtonTap() {
        ((ICounterView)_view).loadCounterAddDialog(players);
    }

    @Override
    public void addCounter(PlayerIdEnum playerIdEnum, Counter counter) {
        for (Player player : players) {
            if (player.getPlayerIdEnum().equals(playerIdEnum)) {
                counter.setIdentifier(player.getPlayerIdEnum().toString() + "-" + player.getAllCounters().size());
                player.addCounter(counter);
                break;
            }
        }

        ((ICounterView)_view).addCounter(playerIdEnum, counter);
    }

    @Override
    public void onPlayerIdentificationChangeConfirmed(PlayerIdEnum playerIdEnum, String newIdentification) {

        for (Player player : players) {
            if (player.getPlayerIdEnum() == playerIdEnum) {
                player.setPlayerIdentification(newIdentification);
                break;
            }
        }

        ((ICounterView)_view).setPlayerIdentificationText(playerIdEnum, newIdentification);
    }

    @Override
    public String getPlayerIdentification(PlayerIdEnum playerIdEnum) {
        for (Player player : players) {
            if (player.getPlayerIdEnum() == playerIdEnum) {
                return player.getPlayerIdentification();
            }
        }
        return "";
    }

    @Override
    public void onPlayerIdentificationTap(PlayerIdEnum playerIdEnum) {
        ((ICounterView)_view).loadPlayerIdentificationDialog(playerIdEnum, getPlayerIdentification(playerIdEnum));
    }

    @Override
    public void onPlayerIdentificationLongTap(PlayerIdEnum playerIdEnum) {
        ((ICounterView)_view).loadPlayerDeletionDialog(playerIdEnum);
    }

    @Override
    public void onPlayerDeletionConfirmed(PlayerIdEnum playerIdEnum) {
        for (Player player : players) {
            if (player.getPlayerIdEnum() == playerIdEnum) {
                player.clearCounters();
                player.setPlayerIdentification("");
                break;
            }
        }
        ((ICounterView)_view).deleteAllCountersForPlayer(playerIdEnum);
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

        ((ICounterView)_view).deleteCounter(counterLayout, deleteParent);
    }

    @Override
    public void onCounterTap(LinearLayout counterLayout) {
        String identifier = counterLayout.getTag().toString();
        ((ICounterView)_view).loadCounterEditDialog(counterLayout, getPlayerByCounterIdentifier(identifier), getCounterByIdentifier(identifier));
    }

    @Override
    public void onCounterLongTap(LinearLayout counterLayout) {
        ((ICounterView)_view).loadCounterDeletionDialog(counterLayout);
    }

    @Override
    public void onCounterEditCompleted(PlayerIdEnum playerIdEnum, String oldCounterIdentifier, Counter newCounter) {
        newCounter.setIdentifier(oldCounterIdentifier);
        switch (playerIdEnum) {
            case ONE:
                players.get(0).updateCounter(newCounter);
                ((ICounterView)_view).updateCounterView(players.get(0), newCounter);
                break;
            case TWO:
                players.get(1).updateCounter(newCounter);
                ((ICounterView)_view).updateCounterView(players.get(1), newCounter);
                break;
            case THREE:
                players.get(2).updateCounter(newCounter);
                ((ICounterView)_view).updateCounterView(players.get(2), newCounter);
                break;
            case FOUR:
                players.get(3).updateCounter(newCounter);
                ((ICounterView)_view).updateCounterView(players.get(3), newCounter);
                break;
            default:
                break;
        }
    }

    private Counter getCounterByIdentifier(String identifier) {
        Player currentPlayer;

        switch (PlayerIdEnum.valueOf((identifier.split("_"))[0])) {
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
        switch (PlayerIdEnum.valueOf((identifier.split("_"))[0])) {
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
