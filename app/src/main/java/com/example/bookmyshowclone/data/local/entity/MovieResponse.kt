package com.example.bookmyshowclone.data.local.entity

import androidx.room.*
import com.example.bookmyshowclone.data.local.typeconverter.MovieTypeConverter

@Entity(tableName = "tbl_movie_data")
data class MovieResponse(
    @PrimaryKey
    val page:Int = 1,

    @ColumnInfo(name = "movie_response")
    @TypeConverters(MovieTypeConverter::class)
    val results:List<Movie>
)