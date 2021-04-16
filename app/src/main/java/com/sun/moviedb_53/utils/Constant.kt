package com.sun.moviedb_53.utils

import com.sun.moviedb_53.BuildConfig

object Constant {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500"
    const val URL_FACEBOOK = "https://www.facebook.com/"
    const val URL_INSTAGRAM = "https://www.instagram.com/"
    const val URL_TWITTER = "https://twitter.com/"
    const val BASE_LANGUAGE = "&language=en-US"
    const val BASE_PAGE = "&page="
    const val BASE_SORT_BY_POPULARITY = "&sort_by=popularity.desc"
    const val BASE_WITH_GENRES = "&with_genres="
    const val BASE_QUERY = "&query="
    const val DEFAULT_PAGE = 1
    const val VISIBLE_THRESHOLD = 3
    const val API_KEY = "api_key="
    const val BASE_WITH_ACTOR = "&with_cast="
    const val BASE_API_KEY = API_KEY + BuildConfig.API_KEY
}
