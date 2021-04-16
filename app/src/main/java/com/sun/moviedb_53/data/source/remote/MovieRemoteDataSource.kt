package com.sun.moviedb_53.data.source.remote

import com.sun.moviedb_53.data.model.GenresMovie
import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.data.model.SearchMovie
import com.sun.moviedb_53.data.source.MovieDataSource
import com.sun.moviedb_53.data.source.remote.fetchjson.GetJsonFromUrl
import com.sun.moviedb_53.utils.*

@Suppress("DEPRECATION")
class MovieRemoteDataSource : MovieDataSource.Remote {

    private val endPointParams = Constant.BASE_API_KEY + Constant.BASE_LANGUAGE

    override fun getHotMovies(
        page: Int,
        hotMovieType: HotMovieType,
        listener: OnFetchDataJsonListener<MutableList<HotMovie?>>
    ) {
        val baseUrl = Constant.BASE_URL + MOVIE_TYPE + hotMovieType.path +
                Constant.BASE_API_KEY +
                Constant.BASE_LANGUAGE +
                Constant.BASE_PAGE + page
        GetJsonFromUrl(listener, KeyEntityType.MOVIE_ITEM).execute(baseUrl)
    }

    override fun <T> getDataInMovieDetail(
        idMovie: Int,
        detailMovieType: DetailMovieType,
        listener: OnFetchDataJsonListener<T>
    ) {
        val stringUrl = Constant.BASE_URL +
                MOVIE_TYPE + idMovie +
                detailMovieType.path +
                endPointParams

        when (detailMovieType) {
            DetailMovieType.MOVIE_DETAIL -> GetJsonFromUrl(
                listener,
                KeyEntityType.MOVIE_DETAIL
            ).execute(stringUrl)
            DetailMovieType.ACTOR -> GetJsonFromUrl(
                listener,
                KeyEntityType.ACTOR
            ).execute(stringUrl)
            DetailMovieType.VIDEO_YOUTUBE -> GetJsonFromUrl(
                listener,
                KeyEntityType.VIDEO_YOUTUBE
            ).execute(stringUrl)
            DetailMovieType.RECOMMENDATIONS -> GetJsonFromUrl(
                listener,
                KeyEntityType.MOVIE_ITEM
            ).execute(stringUrl)
        }

    }

    override fun <T> getGenres(listener: OnFetchDataJsonListener<T>) {
        val stringUrl = Constant.BASE_URL + GENRES_TYPE + endPointParams
        GetJsonFromUrl(listener, KeyEntityType.GENRES_ITEM).execute(stringUrl)
    }

    override fun getGenresMovie(
        page: Int,
        query: String,
        listener: OnFetchDataJsonListener<MutableList<GenresMovie?>>
    ) {
        val stringUrl = Constant.BASE_URL +
                DISCOVER_TYPE +
                endPointParams +
                Constant.BASE_SORT_BY_POPULARITY +
                Constant.BASE_PAGE + page +
                Constant.BASE_WITH_GENRES + query
        GetJsonFromUrl(listener, KeyEntityType.GENRES_MOVIE_ITEM).execute(stringUrl)
    }

    override fun <T> getDataInActor(
        idActor: Int,
        actorDetailType: ActorDetailType,
        listener: OnFetchDataJsonListener<T>
    ) {
        val stringUrl = Constant.BASE_URL +
                ACTOR_TYPE +
                idActor +
                actorDetailType.path +
                endPointParams

        when (actorDetailType) {
            ActorDetailType.ACTOR -> GetJsonFromUrl(
                listener,
                KeyEntityType.ACTOR_DETAIL
            ).execute(stringUrl)
            ActorDetailType.EXTERNAL -> GetJsonFromUrl(
                listener,
                KeyEntityType.EXTERNAL_ACTOR
            ).execute(stringUrl)
        }
    }

    override fun getSearchResult(
        page: Int,
        query: String,
        listener: OnFetchDataJsonListener<MutableList<SearchMovie?>>
    ) {
        val stringUrl = Constant.BASE_URL +
                SEARCH_TYPE +
                endPointParams +
                Constant.BASE_PAGE +
                page +
                Constant.BASE_QUERY +
                query
        GetJsonFromUrl(listener, KeyEntityType.SEARCH_MOVIE_ITEM).execute(stringUrl)
    }

    override fun getMovieByActor(
        idActor: Int,
        listener: OnFetchDataJsonListener<List<HotMovie>>
    ) {
        val stringUrl = Constant.BASE_URL +
                DISCOVER_TYPE +
                endPointParams +
                Constant.BASE_SORT_BY_POPULARITY +
                Constant.BASE_WITH_ACTOR +
                idActor

        GetJsonFromUrl(listener, KeyEntityType.MOVIE_ITEM).execute(stringUrl)
    }

    companion object {
        private const val MOVIE_TYPE = "movie/"
        private const val GENRES_TYPE = "genre/movie/list?"
        private const val DISCOVER_TYPE = "discover/movie?"
        private const val ACTOR_TYPE = "person/"
        private const val SEARCH_TYPE = "search/movie?"

        private var instance: MovieRemoteDataSource? = null

        fun getInstance() = instance ?: MovieRemoteDataSource().also { instance = it }
    }
}
