package com.marceljurtz.lifecounter.Counter;

import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;

import java.util.List;

public class CounterPresenter implements ICounterPresenter {

    private List<Player> players;
    private ICounterView view;

    public CounterPresenter(ICounterView view, List<Player> players) {
        this.players = players;
        this.view = view;
    }

    @Override
    public void onCreate() {

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

    @Override
    public void OnCreateNewCounterButtonTap() {
        view.LoadNewCounterDialog();
    }

    @Override
    public void AddCounterToPlayer(Player player, Counter counter) {
        player.AddCounter(counter);
        view.DisplayNewCounterEntryToPlayer(player, counter);
    }

    @Override
    public void OnPlayerIdentificationChanged(Player player, String newIdentification) {
        player.setPlayerIdentification(newIdentification);
        view.SetPlayerLabelHeader(player, newIdentification);
    }

    @Override
    public void OnPlayerIdentificationTap(Player player) {
        view.LoadPlayerDescriptionDialog(player);
    }
}
