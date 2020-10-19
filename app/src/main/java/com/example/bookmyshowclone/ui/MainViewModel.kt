package com.example.bookmyshowclone.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookmyshowclone.data.remote.MovieRepository
import com.example.bookmyshowclone.data.local.entity.MovieResponse
import com.example.bookmyshowclone.util.NetworkHelper

class MainViewModel(private val networkHelper: NetworkHelper, private val movieRepository: MovieRepository):ViewModel() {

    companion object {
        private const val API_KEY = "9bdd64b74be0c161ef81a7a0be2f1046"
    }

    private val _movieResponse = MutableLiveData<MovieResponse>()
    val movieResponse: LiveData<MovieResponse>
        get() = _movieResponse

    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String>
        get() = _errorResponse

    fun onCreate() {
        if(networkHelper.isNetworkConnected()) {
            movieRepository.fetchMovies(API_KEY, {movieResponse ->
                _movieResponse.postValue(movieResponse)
            }, {errorResponse->
                _errorResponse.postValue(errorResponse)
            })
        }
        else {
            //load from database
            movieRepository.getMoviesLocal { movieResponse ->
                if(movieResponse!=null && movieResponse.results.isNotEmpty()) {
                    _movieResponse.postValue(movieResponse)
                }
                else  {
                    _errorResponse.postValue("Something went wrong.")
                }
            }
        }
    }
}