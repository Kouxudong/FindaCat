package edu.gwu.trivia.model.generated.petfinder1

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Contact(
        @Json(name = "email") val email: StringWrapper,
        @Json(name = "zip") val zip: StringWrapper
): Parcelable