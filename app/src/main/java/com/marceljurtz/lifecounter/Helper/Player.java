package com.marceljurtz.lifecounter.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class Player implements Parcelable {
    private int lifePoints;
    private int poisonPoints;
    private PlayerID playerID;
    private String playerIdentification;
    private ArrayList<Counter> counters;

    private final int DEFAULT_LIFEPOINTS = 20;

    public Player(PlayerID id) {
        this.playerID = id;
        this.lifePoints = DEFAULT_LIFEPOINTS;
        this.poisonPoints = 0;
        counters = new ArrayList<Counter>();
    }

    public String GetJson() {
        return new Gson().toJson(this);
    }

    public static Player GetInstanceByJson(String json) {
        return new Gson().fromJson(json, Player.class);
    }

    public void resetPoints(SharedPreferences preferences) {
        this.lifePoints = PreferenceManager.getDefaultLifepoints(preferences);
        this.poisonPoints = 0;
    }

    public PlayerID getPlayerID() {
        return this.playerID;
    }

    public int getLifePoints() {
        return this.lifePoints;
    }

    public int getPoisonPoints() { return this.poisonPoints; }

    public void updateLifepoints(int amount) {
        this.lifePoints += amount;
        if(this.lifePoints < PreferenceManager.getMinLife()) {
            this.lifePoints = PreferenceManager.getMinLife();
        } else if(this.lifePoints > PreferenceManager.getMaxLife()) {
            this.lifePoints = PreferenceManager.getMaxLife();
        }
    }

    public void updatePoisonpoints(int amount) {
        this.poisonPoints += amount;
        if(this.poisonPoints < PreferenceManager.getMinPoison()) {
            this.poisonPoints = PreferenceManager.getMinPoison();
        } else if(this.poisonPoints > PreferenceManager.getMaxPoison()) {
            this.lifePoints = PreferenceManager.getMaxPoison();
        }
    }

    public String getPlayerIdentification() {
        return playerIdentification;
    }

    public void setPlayerIdentification(String identification){
        playerIdentification = identification;
    }

    public void AddCounter(Counter c) {
        counters.add(c);
    }

    public void RemoveCounter(Counter c) {
        counters.remove(c);
        c = null;
    }

    public ArrayList<Counter> GetAllCounters() {
        return counters;
    }

    @Override
    public String toString() {
        if(playerIdentification == null ||playerIdentification == "") {
            int id = 0;

            switch (playerID) {
                case ONE:
                    id = 1;
                    break;
                case TWO:
                    id = 2;
                    break;
                case THREE:
                    id = 3;
                    break;
                case FOUR:
                    id = 4;
                    break;
                default:
                    break;
            }
            return "Player " + id;
        }
        else return playerIdentification;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(lifePoints);
        dest.writeInt(poisonPoints);
        dest.writeString(playerID.name());
        dest.writeString(playerIdentification);
        dest.writeList(counters);
    }

    private Player(Parcel source) {
        lifePoints = source.readInt();
        poisonPoints = source.readInt();
        playerID = PlayerID.valueOf(source.readString());
        playerIdentification = source.readString();
        counters = new ArrayList<>();
        source.readList(counters, Counter.class.getClassLoader());
    }

    public static final Parcelable.Creator<Player> CREATOR
            = new Parcelable.Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
