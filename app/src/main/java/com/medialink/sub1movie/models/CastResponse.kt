package com.medialink.sub1movie.models

import com.google.gson.annotations.SerializedName

data class CastResponse(

    @field:SerializedName("cast")
    val cast: List<Cast?>? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("crew")
    val crew: List<Crew?>? = null
)