package com.medialink.sub1movie.data

interface MovieDataSource {
    fun retrieveMovie(callback: OperationCallback)
    fun retriveMovieCast(movieId: Int, callback: OperationCallback)
    fun cancel()
}