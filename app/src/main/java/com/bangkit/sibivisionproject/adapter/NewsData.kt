package com.bangkit.sibivisionproject.adapter

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class NewsData (
    val dataImage: Int,
    val dataTitle: String,
    val dataDescription: String

): Parcelable