package com.medialink.sub1movie.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.medialink.sub1movie.R
import com.medialink.sub1movie.di.Injection
import com.medialink.sub1movie.models.Movie
import com.medialink.sub1movie.viewmodels.MovieModelFactory
import com.medialink.sub1movie.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_error.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MovieListAdapter

    companion object {
        const val TAG = "CONSOLE"
    }

    /**
    //Consider this, if you need to call the service once when activity was created.
    Log.v(TAG,"savedInstanceState $savedInstanceState")
    if(savedInstanceState==null){
    viewModel.loadMovies()
    }
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupUI()
    }

    //ui
    private fun setupUI() {
        adapter = MovieListAdapter(this)
        lv_movie.adapter = adapter
        lv_movie.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val movie = adapter.movies[position]
            val movieDetailIntent = Intent(this@MainActivity, MovieDetailActivity::class.java)
            movieDetailIntent.putExtra("PARCEL", movie)
            startActivity(movieDetailIntent)
        }
    }

    //viewmodel
    /**
    //Consider this if ViewModel class don't require parameters.
    viewModel = ViewModelProviders.of(this).get(MuseumViewModel::class.java)

    //if you require any parameters to  the ViewModel consider use a ViewModel Factory
    viewModel = ViewModelProviders.of(this,ViewModelFactory(Injection.providerRepository())).get(MuseumViewModel::class.java)

    //Anonymous observer implementation
    viewModel.museums.observe(this,Observer<List<Museum>> {
    Log.v("CONSOLE", "data updated $it")
    adapter.update(it)
    })
     */
    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, MovieModelFactory(Injection.provideRepository()))
            .get(MovieViewModel::class.java)
        viewModel.movies.observe(this, renderMovies)

        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    private val renderMovies = Observer<List<Movie>> {
        Log.v(TAG, "data updated $it")
        layout_error.visibility = View.GONE
        layout_empty.visibility = View.GONE
        adapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        progress_main.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        layout_error.visibility = View.VISIBLE
        layout_empty.visibility = View.GONE
        txt_error.text = "Error $it"
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        layout_empty.visibility = View.VISIBLE
        layout_error.visibility = View.GONE
    }

    //If you require updated data, you can call the method "loadMuseum" here
    override fun onResume() {
        super.onResume()
        //viewModel.loadMovies()
    }
}


