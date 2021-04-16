package com.sun.moviedb_53.data.source.repository

import com.sun.moviedb_53.data.model.*
import com.sun.moviedb_53.data.source.MovieDataSource
import com.sun.moviedb_53.data.source.remote.OnFetchDataJsonListener
import com.sun.moviedb_53.utils.ActorDetailType
import com.sun.moviedb_53.utils.DetailMovieType
import com.sun.moviedb_53.utils.HotMovieType

class MovieRepository private constructor(
    private val remote: MovieDataSource.Remote
) {

    fun getMovie(
        page: Int,
        hotMovieType: HotMovieType,
        listener: OnFetchDataJsonListener<MutableList<HotMovie?>>
    ) {
        remote.getHotMovies(page, hotMovieType, listener)
    }

    fun getMovieDetails(id: Int, listener: OnFetchDataJsonListener<MovieDetail>) {
        remote.getDataInMovieDetail(id, DetailMovieType.MOVIE_DETAIL, listener)
    }

    fun getListActorInMovieDetail(
        idMovieDetails: Int,
        listener: OnFetchDataJsonListener<List<Actor>>
    ) {
        remote.getDataInMovieDetail(idMovieDetails, DetailMovieType.ACTOR, listener)
    }

    fun getListVideoYoutubeInMovieDetail(
        idMovieDetails: Int,
        listener: OnFetchDataJsonListener<List<VideoYoutube>>
    ) {
        remote.getDataInMovieDetail(idMovieDetails, DetailMovieType.VIDEO_YOUTUBE, listener)
    }

    fun getListRecommendationsInMovieDetail(
        idMovieDetails: Int,
        listener: OnFetchDataJsonListener<List<HotMovie>>
    ) {
        remote.getDataInMovieDetail(idMovieDetails, DetailMovieType.RECOMMENDATIONS, listener)
    }

    fun getActorDetail(
        idActor: Int,
        listener: OnFetchDataJsonListener<ActorDetail>
    ) {
        remote.getDataInActor(idActor, ActorDetailType.ACTOR, listener)
    }

    fun getExternal(
        idActor: Int,
        listener: OnFetchDataJsonListener<External>
    ) {
        remote.getDataInActor(idActor, ActorDetailType.EXTERNAL, listener)
    }

    companion object {
        private var instance: MovieRepository? = null

        fun getInstance(
            remote: MovieDataSource.Remote
        ): MovieRepository {
            return instance ?: synchronized(this) {
                instance ?: MovieRepository(remote).also { instance = it }
            }
        }
    }
}
