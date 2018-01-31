package com.marceljurtz.lifecounter.Helper;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Player implements Parcelable {
    private int lifePoints;
    private int poisonPoints;
    private PlayerID playerID;
    private String playerIdentification;
    private ArrayList<Counter> counters;
    private Color color;

    private final int DEFAULT_LIFEPOINTS = 20;

    public Player(PlayerID id) {
        this.playerID = id;
        this.lifePoints = DEFAULT_LIFEPOINTS;
        this.poisonPoints = 0;
        counters = new ArrayList<Counter>();
        color = Color.getDefaultColor(MagicColor.BLACK);
    }

    private Player(Parcel source) {
        lifePoints = source.readInt();
        poisonPoints = source.readInt();
        playerID = PlayerID.valueOf(source.readString());
        playerIdentification = source.readString();
        color = Color.getDefaultColor(MagicColor.valueOf(source.readString()));
        counters = new ArrayList<>();
        source.readList(counters, Counter.class.getClassLoader());
    }

    public String getJson() {
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

    public int getPoisonPoints() {
        return this.poisonPoints;
    }

    public void updateLifepoints(int amount) {
        this.lifePoints += amount;
        if (this.lifePoints < PreferenceManager.getMinLife()) {
            this.lifePoints = PreferenceManager.getMinLife();
        } else if (this.lifePoints > PreferenceManager.getMaxLife()) {
            this.lifePoints = PreferenceManager.getMaxLife();
        }
    }

    public void updatePoisonpoints(int amount) {
        this.poisonPoints += amount;
        if (this.poisonPoints < PreferenceManager.getMinPoison()) {
            this.poisonPoints = PreferenceManager.getMinPoison();
        } else if (this.poisonPoints > PreferenceManager.getMaxPoison()) {
            this.lifePoints = PreferenceManager.getMaxPoison();
        }
    }

    public String getPlayerIdentification() {
        if (playerIdentification == null || playerIdentification.trim() == "") {
            switch (playerID) {
                case ONE:
                    playerIdentification = "Player 1";
                    break;
                case TWO:
                    playerIdentification = "Player 2";
                    break;
                case THREE:
                    playerIdentification = "Player 3";
                    break;
                case FOUR:
                    playerIdentification = "Player 4";
                    break;
                default:
                    break;
            }
        }
        return playerIdentification;
    }

    public void setPlayerIdentification(String identification) {
        playerIdentification = identification;
    }

    public void addCounter(Counter c) {
        c.setIdentifier(playerID.toString() + "_" + counters.size());
        counters.add(c);
    }

    public void removeCounter(Counter c) {
        counters.remove(c);
        c = null;
    }

    public void updateCounter(Counter counter) {
        for (Counter c: counters) {
            if(c.getIdentifier() == counter.getIdentifier()) {

                c.setATK(counter.getATK());
                c.setDEF(counter.getDEF());
                c.setDescription(counter.getDescription());

                return;
            }
        }
    }

    public ArrayList<Counter> getAllCounters() {
        return counters;
    }

    public void clearCounters() {
        counters.clear();
    }

    public Color getColorOrDefault() {
        if(color == null) {
            color = Color.getDefaultColor(MagicColor.BLACK);
        }
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return getPlayerIdentification();
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
        dest.writeValue(color);
        dest.writeList(counters);
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
