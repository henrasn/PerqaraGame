package com.henra.perqaragame.data.source.remote

import com.henra.perqaragame.data.model.dto.DetailGameResponse
import com.henra.perqaragame.data.model.dto.GamesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    suspend fun fetchGames(
        @Query("page") page: Int,
        @Query("page_size") size: Int,
        @Query("search") query: String
    ): Response<GamesResponse>

    @GET("games/{id}")
    suspend fun fetchDetailGame(
        @Path("id") id: Int
    ): Response<DetailGameResponse>
}