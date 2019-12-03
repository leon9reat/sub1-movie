package com.medialink.sub1movie.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medialink.sub1movie.data.MovieDataSource
import com.medialink.sub1movie.data.OperationCallback
import com.medialink.sub1movie.models.Cast
import com.medialink.sub1movie.models.Movie

class MovieViewModel(private val repository: MovieDataSource) : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>().apply { value = emptyList() }
    val movies: LiveData<List<Movie>> = _movies

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    private val _movieCasts = MutableLiveData<List<Cast>>().apply { value = emptyList() }
    val movieCasts: LiveData<List<Cast>> = _movieCasts

    /*
    If you require that the data be loaded only once, you can consider calling the method
    "loadMovies()" on constructor. Also, if you rotate the screen, the service will not be called.
     */
    init {
        loadMovies()
    }

    fun loadMovies() {
        _isViewLoading.postValue(true)
        repository.retrieveMovie(object : OperationCallback {
            override fun onSuccess(obj: Any?) {
                _isViewLoading.postValue(false)
                if (obj != null && obj is List<*>) {
                    if (obj.isEmpty()) {
                        _isEmptyList.postValue(true)
                    } else {
                        _movies.value = obj as List<Movie>
                    }
                }
            }

            override fun onError(obj: Any?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(obj)
            }

        })
    }

    fun loadMovieCast(movieId: Int) {
        _isViewLoading.postValue(true)
        repository.retriveMovieCast(movieId, object : OperationCallback {
            override fun onSuccess(obj: Any?) {
                _isViewLoading.postValue(false)
                if (obj != null && obj is List<*>) {
                    if (obj.isEmpty()) {
                        _isEmptyList.postValue(true)
                    } else {
                        _movieCasts.value = obj as List<Cast>
                    }
                }
            }

            override fun onError(obj: Any?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(obj)
            }

        })
    }

}