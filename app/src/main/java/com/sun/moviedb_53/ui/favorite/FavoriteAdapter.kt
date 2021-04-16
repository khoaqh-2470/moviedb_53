package com.sun.moviedb_53.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.moviedb_53.R
import com.sun.moviedb_53.data.model.Favorite
import com.sun.moviedb_53.extensions.loadFromUrl
import com.sun.moviedb_53.extensions.toGone
import com.sun.moviedb_53.extensions.toVisible
import com.sun.moviedb_53.utils.Constant
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoriteAdapter(
    private val onItemClick: (Int) -> Unit,
    private val onItemDelete: (Int, Int) -> Unit
) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favorites = mutableListOf<Favorite>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view, onItemClick, onItemDelete)
    }

    override fun getItemCount() = favorites.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindData(favorites[position])
    }

    fun setData(newList: MutableList<Favorite>) {
        favorites = newList
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        favorites.removeAt(position)
        notifyItemRangeRemoved(position, favorites.size)
    }

    class FavoriteViewHolder(
        itemView: View,
        private val onItemClick: (Int) -> Unit,
        private val onItemDelete: (Int, Int) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {

        fun bindData(favorite: Favorite) {
            itemView.apply {
                favorite.run {
                    if (title.isEmpty()) textTitle.toGone() else textTitle.toVisible()
                    if (tagLine.isEmpty()) textTagLine.toGone() else textTagLine.toVisible()

                    textTitle.text = title
                    textTagLine.text = tagLine
                    ratingBar.rating = (rate / 2).toFloat()
                    imageFavorite.loadFromUrl(Constant.BASE_URL_IMAGE + imagePoster)

                    setOnClickListener {
                        onItemClick(id)
                    }
                    setOnLongClickListener {
                        onItemDelete(id, adapterPosition)
                        true
                    }
                }
            }
        }
    }
}
