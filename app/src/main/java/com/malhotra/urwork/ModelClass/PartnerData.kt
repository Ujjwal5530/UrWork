package com.malhotra.urwork.ModelClass

import android.os.Parcel
import android.os.Parcelable
data class PartnerData(var id : String? = null,
                                val name : String? = null,
                                val email : String? = null,
                                val phone : String? = null,
                                val category : String? = null,
                                val subCategory: String? = null,
    val city : String? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(category)
        parcel.writeString(subCategory)
        parcel.writeString(city)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PartnerData> {
        override fun createFromParcel(parcel: Parcel): PartnerData {
            return PartnerData(parcel)
        }

        override fun newArray(size: Int): Array<PartnerData?> {
            return arrayOfNulls(size)
        }
    }
}
