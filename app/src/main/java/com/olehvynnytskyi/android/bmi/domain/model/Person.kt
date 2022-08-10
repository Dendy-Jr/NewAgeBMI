package com.olehvynnytskyi.android.bmi.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val name: String = "",
    val weight: Double = 0.0,
    val height: Double = 0.0,
    val gender: Gender = Gender.MALE
) : Parcelable {

    @Parcelize
    enum class Gender(val value: String) : Parcelable {
        MALE("Male"),
        FEMALE("Female")
    }
}