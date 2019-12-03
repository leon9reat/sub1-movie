package com.medialink.sub1movie.network

import com.medialink.sub1movie.models.CastResponse
import com.medialink.sub1movie.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMovie {
    @GET("movie/popular")
    fun getMoviePopular(@Query("page") page: Int): Call<MovieResponse>

    @GET("movie/{id}/credits")
    fun getCredit(@Path("id") id: Int): Call<CastResponse>
}