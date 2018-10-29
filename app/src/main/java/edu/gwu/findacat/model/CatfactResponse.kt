package edu.gwu.findacat.model

import com.squareup.moshi.Json

data class CatfactResponse(

	@Json(name="fact")
	val fact: String,

	@Json(name="length")
	val length: Int? = null
)