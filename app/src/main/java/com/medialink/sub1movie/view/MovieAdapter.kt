package com.medialink.sub1movie.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.medialink.sub1movie.R
import com.medialink.sub1movie.models.Movie
import kotlinx.android.synthetic.main.main_item.view.*

class MovieAdapter(private var movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movies[position]
        holder.view.txt_title.text = movie.title
        holder.view.txt_overview.text = movie.overview
        Glide.with(holder.view.img_poster.context)
            .load(movie.posterPath)
            .into(holder.view.img_poster)
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }

    fun update(data: List<Movie>) {
        movies = data
        notifyDataSetChanged()
    }
}