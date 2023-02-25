package dev.cisnux.myintentapp

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// with parcelize plugin
@Parcelize
data class Person(
    val name: String?,
    val age: Int?,
    val email: String?,
    val city: String?
) : Parcelable

// without kotlin-parcelize plugin
//data class Person(
//    val name: String?,
//    val age: Int?,
//    val email: String?,
//    val city: String?r
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readValue(Int::class.java.classLoader) as? Int,
//        parcel.readString(),
//        parcel.readString()
//    )
//
//    override fun describeContents(): Int = 0
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) =
//        parcel.run {
//            writeString(name)
//            writeValue(age)
//            writeString(email)
//            writeString(city)
//        }
//
//    companion object CREATOR : Parcelable.Creator<Person> {
//        override fun createFromParcel(parcel: Parcel): Person = Person(parcel)
//
//        override fun newArray(size: Int): Array<Person?> = arrayOfNulls(size)
//    }
//}
