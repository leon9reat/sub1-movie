package com.medialink.sub1movie.data

interface MovieDataSource {
    fun retrieveMovie(callback: OperationCallback)
    fun cancel()
}