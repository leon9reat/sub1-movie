package com.medialink.sub1movie.network

object ApiFactory {
    val apiMovie: ApiMovie = ApiClient.retrofit().create(ApiMovie::class.java)
}