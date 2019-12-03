package com.medialink.sub1movie.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.medialink.sub1movie.Const
import com.medialink.sub1movie.R
import com.medialink.sub1movie.models.Cast
import kotlinx.android.synthetic.main.cast_item.view.*

class MovieDetailAdapter(private var casts: List<Cast>) :
    RecyclerView.Adapter<MovieDetailAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cast_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = casts.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cast: Cast = casts[position]
        holder.view.txt_name.text = cast.name
        Glide.with(holder.view.img_cast.context)
            .load("${Const.TMDB_PHOTO_URL}${cast.profilePath}")
            .transform(CircleCrop())
            .into(holder.view.img_cast)
    }

    fun update(list: List<Cast>) {
        casts = list
        notifyDataSetChanged()
    }
}