package com.medialink.sub1movie

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.medialink.sub1movie.data.MovieRepository
import com.medialink.sub1movie.data.OperationCallback
import com.medialink.sub1movie.models.Movie
import com.medialink.sub1movie.viewmodels.MovieViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*

class MovieTest {
    @Mock
    private lateinit var repository: MovieRepository
    @Mock
    private lateinit var context: Application
    @Captor
    private lateinit var callbackCaptor: ArgumentCaptor<OperationCallback>

    private lateinit var viewModel: MovieViewModel

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var emptyListObserver: Observer<Boolean>
    private lateinit var onRenderMoviesObserver: Observer<List<Movie>>

    private lateinit var movieEmptyList: List<Movie>
    private lateinit var movieList: List<Movie>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`<Context>(context.applicationContext).thenReturn(context)

        viewModel = MovieViewModel(repository)

        mockData()
        setupObservers()
    }

    private fun mockData() {
        movieEmptyList = emptyList()
        val mockList: MutableList<Movie> = mutableListOf()
        mockList.add(Movie(id = 0, title = "Judul 1", overview = ""))
        mockList.add(Movie(id = 1, title = "Judul 2", overview = ""))
        mockList.add(Movie(id = 2, title = "Judul 3", overview = ""))

        movieList = mockList.toList()
    }

    private fun setupObservers() {
        isViewLoadingObserver = Mockito.mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver = Mockito.mock(Observer::class.java) as Observer<Any>
        emptyListObserver = Mockito.mock(Observer::class.java) as Observer<Boolean>
        onRenderMoviesObserver = Mockito.mock(Observer::class.java) as Observer<List<Movie>>
    }

    @Test
    fun movieEmptyListRepositoryAndViewModel() {
        with(viewModel) {
            loadMovies()
            isViewLoading.observeForever(isViewLoadingObserver)
            //onMessageError.observeForever(onMessageErrorObserver)
            isEmptyList.observeForever(emptyListObserver)
            movies.observeForever(onRenderMoviesObserver)
        }

        Mockito.verify(repository, Mockito.times(1)).retrieveMovie(capture(callbackCaptor))
        callbackCaptor.value.onSuccess(movieEmptyList)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        //Assert.assertNotNull(viewModel.onMessageError.value) //java.lang.AssertionError
        //Assert.assertNotNull(viewModel.isEmptyList.value)
        Assert.assertTrue(viewModel.isEmptyList.value == true)
        Assert.assertTrue(viewModel.movies.value?.size == 0)
    }

    @Test
    fun movieListRepositoryAndViewModel() {
        with(viewModel) {
            loadMovies()
            isViewLoading.observeForever(isViewLoadingObserver)
            movies.observeForever(onRenderMoviesObserver)
        }

        Mockito.verify(repository, Mockito.times(1)).retrieveMovie(capture(callbackCaptor))
        callbackCaptor.value.onSuccess(movieList)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertTrue(viewModel.movies.value?.size == 3)
    }

    @Test
    fun movieFailRepositoryAndViewModel() {
        with(viewModel) {
            loadMovies()
            isViewLoading.observeForever(isViewLoadingObserver)
            onMessageError.observeForever(onMessageErrorObserver)
        }
        Mockito.verify(repository, Mockito.times(1)).retrieveMovie(capture(callbackCaptor))
        callbackCaptor.value.onError("Error Loading")
        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertNotNull(viewModel.onMessageError.value)
    }

}