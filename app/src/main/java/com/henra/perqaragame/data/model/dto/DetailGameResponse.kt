package com.henra.perqaragame.data.model.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailGameResponse(

	@Json(name = "description")
	val description: String,

	@Json(name = "publishers")
	val publishers: List<PublishersItem>,

	@Json(name = "id")
	val id: Int,

	@Json(name = "playtime")
	val playtime: Int
)

@JsonClass(generateAdapter = true)
data class PublishersItem(

	@Json(name = "games_count")
	val gamesCount: Int,

	@Json(name = "name")
	val name: String,

	@Json(name = "id")
	val id: Int,

	@Json(name = "image_background")
	val imageBackground: String,

	@Json(name = "slug")
	val slug: String
)
