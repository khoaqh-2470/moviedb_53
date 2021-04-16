package com.sun.moviedb_53.ui.detail.actor

import com.sun.moviedb_53.data.model.*
import com.sun.moviedb_53.utils.BasePresenter

interface ActorContact {

    interface View {
        fun loadContentActorOnSuccess(actorDetail: ActorDetail)
        fun loadContentExternalOnSuccess(external: External)
        fun onError(exception: Exception?)
    }

    interface Presenter : BasePresenter<View> {
        fun getActorDetail(id: Int)
        fun getExternal(id: Int)
        fun getListMovieOfActor(id: Int)
    }
}
