package com.example.bookmyshowclone.data.remote

import com.example.bookmyshowclone.data.local.entity.MovieResponse

interface MovieRepository {
    fun fetchMovies(api_key:String, onSuccess:(MovieResponse)->Unit, onError:(String)->Unit)
    fun getMoviesLocal(onSuccess: (MovieResponse?) -> Unit)
}