package com.medialink.sub1movie.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.medialink.sub1movie.Const
import com.medialink.sub1movie.R
import com.medialink.sub1movie.di.Injection
import com.medialink.sub1movie.models.Cast
import com.medialink.sub1movie.models.Movie
import com.medialink.sub1movie.viewmodels.MovieModelFactory
import com.medialink.sub1movie.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieDetailAdapter: MovieDetailAdapter
    private var mListCast = arrayListOf<Cast>()


    companion object {
        const val TAG = "MovieDetailActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        supportActionBar?.title = "Movie Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent.getParcelableExtra<Movie>("PARCEL")
        txt_title.text = movie?.title
        txt_release.text = movie?.releaseDate
        txt_vote.text = movie?.voteAverage.toString()
        txt_overview.text = movie?.overview
        Glide.with(this)
            .load("${Const.TMDB_PHOTO_URL2}${movie?.posterPath}")
            .into(img_poster_detail)

        setupViewModel()
        setupUi()

        if (savedInstanceState == null) movie.id?.let { movieViewModel.loadMovieCast(it) }
    }

    private fun setupViewModel() {
        movieViewModel =
            ViewModelProviders.of(this, MovieModelFactory(Injection.provideRepository()))
                .get(MovieViewModel::class.java)

        movieViewModel.movieCasts.observe(this, castObserver)
    }

    private val castObserver = Observer<List<Cast>> {
        movieDetailAdapter.update(it)
        if (it.isNotEmpty()) mListCast = it as ArrayList<Cast>
    }

    private fun setupUi() {
        movieDetailAdapter = MovieDetailAdapter(mListCast)
        rv_detail.setHasFixedSize(true)
        rv_detail.adapter = movieDetailAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
