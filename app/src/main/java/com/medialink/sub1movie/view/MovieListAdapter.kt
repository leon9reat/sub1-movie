package com.medialink.sub1movie.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.medialink.sub1movie.Const
import com.medialink.sub1movie.R
import com.medialink.sub1movie.models.Movie

class MovieListAdapter internal constructor(private val context: Context) : BaseAdapter() {
    internal var movies = arrayListOf<Movie>()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var itemView = view
        if (itemView == null) {
            itemView = LayoutInflater.from(context)
                .inflate(R.layout.main_item, parent, false)
        }

        val viewHolder = ViewHolder(itemView as View)
        val movie = getItem(position) as Movie
        viewHolder.bind(movie)
        return itemView
    }

    override fun getItem(position: Int): Any = movies[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = movies.size

    private inner class ViewHolder internal constructor(view: View) {
        private val txtTitle: TextView = view.findViewById(R.id.txt_title)
        private val txtOverview: TextView = view.findViewById(R.id.txt_overview)
        private val imgPoster: ImageView = view.findViewById(R.id.img_poster)

        internal fun bind(movie: Movie) {
            txtTitle.text = movie.title
            txtOverview.text = movie.overview
            Glide.with(context)
                .load("${Const.TMDB_PHOTO_URL}${movie.posterPath}")
                .fitCenter()
                .into(imgPoster)
        }
    }

    fun update(data: List<Movie>) {
        if (data.isNotEmpty()) {
            movies = data as ArrayList<Movie>
            notifyDataSetChanged()
        }
    }

}