package com.sun.moviedb_53.ui.favorite

import com.sun.moviedb_53.data.model.Favorite
import com.sun.moviedb_53.utils.BasePresenter

interface FavoriteContact {

    interface View {
        fun loadFavoriteOnSuccess(movies: List<Favorite>)
    }

    interface Presenter : BasePresenter<View> {
        fun getFavorites(): MutableList<Favorite>
        fun deleteFavorite(idMovieDetail: Int): Boolean
    }
}
