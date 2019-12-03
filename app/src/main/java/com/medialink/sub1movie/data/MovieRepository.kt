package com.medialink.sub1movie.data

import android.util.Log
import com.medialink.sub1movie.models.CastResponse
import com.medialink.sub1movie.models.MovieResponse
import com.medialink.sub1movie.network.ApiFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "MovieRepository"

class MovieRepository : MovieDataSource {
    private var call: Call<MovieResponse>? = null
    var mPage: Int = 1

    override fun retrieveMovie(callback: OperationCallback) {
        call = ApiFactory.apiMovie.getMoviePopular(mPage)
        call?.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        Log.i(TAG, "total result: ${it.totalResults}")
                        callback.onSuccess(it.results)
                    } else {
                        callback.onError(response.body())
                    }
                }
            }

        })
    }

    override fun retriveMovieCast(movidId: Int, callback: OperationCallback) {
        ApiFactory.apiMovie.getCredit(movidId)
            .enqueue(object : Callback<CastResponse> {
                override fun onFailure(call: Call<CastResponse>, t: Throwable) {
                    callback.onError(t.message)
                }

                override fun onResponse(
                    call: Call<CastResponse>,
                    response: Response<CastResponse>
                ) {
                    response.body()?.let {
                        if (response.isSuccessful) {
                            Log.i(TAG, "id ${it.id}")
                            callback.onSuccess(it.cast)
                        } else {
                            callback.onError(response.body())
                        }
                    }
                }

            })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}