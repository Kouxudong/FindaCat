package edu.gwu.trivia.model.generated.petfinder1

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photos(@Json(name = "photo") val photo: List<PhotoItem>): Parcelable