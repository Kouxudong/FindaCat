package edu.gwu.trivia.model.generated.petfinder1

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
@Parcelize
data class Breeds(@Json(name = "breed") val breed: List<StringWrapper>): Parcelable