package com.example.bookmyshowclone.data.remote

import com.example.bookmyshowclone.data.local.dao.MovieDao
import com.example.bookmyshowclone.data.local.entity.MovieResponse
import com.example.bookmyshowclone.data.remote.retrofit.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepositoryImpl(private val movieDao: MovieDao, private val request: MovieService): MovieRepository {

    override fun fetchMovies(api_key: String, onSuccess:(MovieResponse)->Unit, onError:(String)->Unit) {
        //make API call
        val call = request.getMovies(api_key)

        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful && response.body()!=null) {
                    //success
                    Thread{
                        movieDao.insertMovies(response.body()!!)
                        onSuccess(response.body()!!)
                    }.start()
                }
                else {
                    //error
                    onError(response.message())
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                //failure
                onError(t.localizedMessage ?: "Something went wrong.")
            }
        })
    }

    override fun getMoviesLocal(onSuccess: (MovieResponse?) -> Unit) {
        Thread{
            onSuccess(movieDao.getMovies())
        }.start()
    }
}