package com.sun.moviedb_53.data.source

import com.sun.moviedb_53.data.model.*
import com.sun.moviedb_53.data.source.remote.OnFetchDataJsonListener
import com.sun.moviedb_53.utils.ActorDetailType
import com.sun.moviedb_53.utils.DetailMovieType
import com.sun.moviedb_53.utils.HotMovieType

interface MovieDataSource {

    interface Local {
        fun saveMovie(favorite: Favorite): Boolean
        fun getListFavorite(): MutableList<Favorite>
        fun deleteFavorite(idMovie: Int): Boolean
        fun checkFavorite(idMovie: Int): Boolean
    }

    interface Remote {
        fun getHotMovies(
            page: Int,
            hotMovieType: HotMovieType,
            listener: OnFetchDataJsonListener<MutableList<HotMovie?>>
        )

        fun <T> getDataInMovieDetail(
            idMovie: Int,
            detailMovieType: DetailMovieType,
            listener: OnFetchDataJsonListener<T>
        )

        fun <T> getGenres(listener: OnFetchDataJsonListener<T>)

        fun getGenresMovie(
            page: Int,
            query: String,
            listener: OnFetchDataJsonListener<MutableList<GenresMovie?>>
        )

        fun <T> getDataInActor(
            idActor: Int,
            actorDetailType: ActorDetailType,
            listener: OnFetchDataJsonListener<T>
        )
    }
}
