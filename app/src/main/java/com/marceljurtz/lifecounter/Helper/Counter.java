package com.marceljurtz.lifecounter.Helper;

import android.os.Parcel;
import android.os.Parcelable;

public class Counter implements Parcelable {

    public Counter(String description, int ATK, int DEF) {
        this.description = description;
        this.atk = ATK;
        this.def = DEF;
    }

    public Counter(String identifier, String description, int ATK, int DEF) {
        this.identifier = identifier;
        this.description = description;
        this.atk = ATK;
        this.def = DEF;
    }

    // ID to link counter to view
    // template: playerID_increment
    private String identifier;

    public String GetIdentifier() {
        return identifier;
    }

    public void SetIdentifier (String identifier) {
        this.identifier = identifier;
    }

    private String description;

    public String GetDescription() {
        return this.description;
    }

    public void SetDescription(String description) {
        this.description = description;
    }

    private int atk;

    public int GetATK() {
        return this.atk;
    }

    public void SetATK(int atk) {
        this.atk = atk;
    }

    private int def;

    public int GetDEF() {
        return this.def;
    }

    public void SetDEF(int def) {
        this.def = def;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(identifier);
        dest.writeString(description);
        dest.writeInt(atk);
        dest.writeInt(def);
    }

    private Counter(Parcel source) {
        identifier = source.readString();
        description = source.readString();
        atk = source.readInt();
        def = source.readInt();
    }

    public static final Parcelable.Creator<Counter> CREATOR
            = new Parcelable.Creator<Counter>() {
        @Override
        public Counter createFromParcel(Parcel in) {
            return new Counter(in);
        }

        @Override
        public Counter[] newArray(int size) {
            return new Counter[size];
        }
    };
}
