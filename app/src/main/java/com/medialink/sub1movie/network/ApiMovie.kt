package com.medialink.sub1movie.network

import com.medialink.sub1movie.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMovie {
    @GET("movie/popular")
    fun getMoviePopular(@Query("page") page: Int): Call<MovieResponse>
}