package com.marceljurtz.lifecounter.Helper;

import android.os.Parcel;
import android.os.Parcelable;

public class Counter implements Parcelable {

    private String description;
    private int atk;
    private int def;

    public Counter(String description, int ATK, int DEF) {
        this.description = description;
        this.atk = ATK;
        this.def = DEF;
    }

    public int getATK() {
        return this.atk;
    }

    public int getDEF() {
        return this.def;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeInt(atk);
        dest.writeInt(def);
    }

    private Counter(Parcel source) {
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
