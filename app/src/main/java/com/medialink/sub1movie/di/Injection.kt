package com.medialink.sub1movie.di

import com.medialink.sub1movie.data.MovieDataSource
import com.medialink.sub1movie.data.MovieRepository

object Injection {
    //MovieRepository could be a singleton
    fun provideRepository(): MovieDataSource {
        return MovieRepository()
    }
}