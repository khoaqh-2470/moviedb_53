package com.sun.moviedb_53.ui.detail.actor

import com.sun.moviedb_53.data.model.ActorDetail
import com.sun.moviedb_53.data.model.External
import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.data.source.remote.OnFetchDataJsonListener
import com.sun.moviedb_53.data.source.repository.MovieRepository

class ActorDetailPresenter(
    private val repository: MovieRepository
) : ActorContact.Presenter {

    private var view: ActorContact.View? = null

    override fun getActorDetail(id: Int) {
        repository.getActorDetail(id, object : OnFetchDataJsonListener<ActorDetail> {
            override fun onSuccess(data: ActorDetail) {
                view?.loadContentActorOnSuccess(data)
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }

    override fun getExternal(id: Int) {
        repository.getExternal(id, object : OnFetchDataJsonListener<External> {
            override fun onSuccess(data: External) {
                view?.loadContentExternalOnSuccess(data)
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }

    override fun getListMovieOfActor(id: Int) {
        repository.getMovieByActor(id, object : OnFetchDataJsonListener<List<HotMovie>> {
            override fun onSuccess(data: List<HotMovie>) {
                view?.loadMoviesOnSuccess(data)
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }

    override fun onStart() {}

    override fun onStop() {
        view = null
    }

    override fun setView(view: ActorContact.View?) {
        this.view = view
    }
}
