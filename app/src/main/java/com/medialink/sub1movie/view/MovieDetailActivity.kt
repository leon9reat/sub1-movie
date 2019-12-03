package com.medialink.sub1movie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.medialink.sub1movie.Const
import com.medialink.sub1movie.R
import com.medialink.sub1movie.models.Movie
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
