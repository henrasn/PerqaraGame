package com.henra.perqaragame.data.model.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GamesResponse(

	@Json(name = "next")
	val next: String?,

	@Json(name = "previous")
	val previous: String?,

	@Json(name = "count")
	val count: Int,

	@Json(name = "results")
	val games: List<GamesItem>
)

@JsonClass(generateAdapter = true)
data class GamesItem(

	@Json(name = "background_image")
	val backgroundImage: String,

	@Json(name = "name")
	val name: String,

	@Json(name = "rating")
	val rating: Float,

	@Json(name = "id")
	val id: Int,

	@Json(name = "released")
	val released: String
)
