package com.example.apprication_task.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Long,
    var firstname: String?,
    var lastname: String?,
    var email: String?,
    var number: String?,
    var image: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(firstname)
        parcel.writeString(lastname)
        parcel.writeString(email)
        parcel.writeString(number)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserEntity> {
        override fun createFromParcel(parcel: Parcel): UserEntity {
            return UserEntity(parcel)
        }

        override fun newArray(size: Int): Array<UserEntity?> {
            return arrayOfNulls(size)
        }
    }
}