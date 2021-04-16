package com.sun.moviedb_53.ui.detail.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.moviedb_53.R
import com.sun.moviedb_53.data.model.Actor
import com.sun.moviedb_53.extensions.loadFromUrl
import com.sun.moviedb_53.utils.Constant
import kotlinx.android.synthetic.main.item_actor.view.*

class ActorAdapter(private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    private var actors = listOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_actor, parent, false)
        return ActorViewHolder(view, onItemClick)
    }

    override fun getItemCount() = actors.size

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bindData(actors[position])
    }

    fun setData(newList: List<Actor>) {
        actors = newList
        notifyDataSetChanged()
    }

    class ActorViewHolder(itemView: View, private val onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bindData(actor: Actor) {
            itemView.apply {
                actor.run {
                    imageActor.loadFromUrl(Constant.BASE_URL_IMAGE + imageUrl)
                    setOnClickListener {
                        id.let(onItemClick)
                    }
                }
            }
        }
    }
}
