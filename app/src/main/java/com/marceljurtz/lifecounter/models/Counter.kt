package com.marceljurtz.lifecounter.models

import android.os.Parcel
import android.os.Parcelable

import com.marceljurtz.lifecounter.enums.CounterType

class Counter : Parcelable {

    // ID to link counterToAdd to view
    // template: playerID_increment
    var identifier: String? = null

    var description: String? = null

    var atk: Int = 0

    var def: Int = 0

    var counterType: CounterType? = null

    constructor(description: String, ATK: Int, DEF: Int, counterType: CounterType) {
        this.description = description
        this.atk = ATK
        this.def = DEF
        this.counterType = counterType
    }

    constructor(identifier: String, description: String, ATK: Int, DEF: Int, counterType: CounterType) {
        this.identifier = identifier
        this.description = description
        this.atk = ATK
        this.def = DEF
        this.counterType = counterType
    }

    private constructor(source: Parcel) {
        identifier = source.readString()
        description = source.readString()
        atk = source.readInt()
        def = source.readInt()
        counterType = CounterType.valueOf(source.readString())
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(identifier)
        dest.writeString(description)
        dest.writeInt(atk)
        dest.writeInt(def)
        dest.writeString(this.counterType!!.name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Counter> = object : Parcelable.Creator<Counter> {
            override fun createFromParcel(`in`: Parcel): Counter {
                return Counter(`in`)
            }

            override fun newArray(size: Int): Array<Counter?> {
                return arrayOfNulls(size)
            }
        }
    }
}
