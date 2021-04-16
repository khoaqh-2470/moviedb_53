package com.sun.moviedb_53.ui.detail.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.moviedb_53.R
import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.extensions.loadFromUrl
import com.sun.moviedb_53.utils.Constant
import kotlinx.android.synthetic.main.item_recommendations.view.*

class RecommendationAdapter(private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecommendationAdapter.RecommendViewHolder>() {

    private var movies = listOf<HotMovie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommendations, parent, false)
        return RecommendViewHolder(view, onItemClick)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        holder.bindData(movies[position])
    }

    fun setData(newList: List<HotMovie>) {
        movies = newList
        notifyDataSetChanged()
    }

    class RecommendViewHolder(itemView: View, private val onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bindData(hotMovie: HotMovie) {
            itemView.apply {
                hotMovie.run {
                    textRecommendation.text = title
                    imageRecommendation.loadFromUrl(Constant.BASE_URL_IMAGE + posterPath)
                    setOnClickListener {
                        id?.let(onItemClick)
                    }
                }
            }
        }
    }
}
