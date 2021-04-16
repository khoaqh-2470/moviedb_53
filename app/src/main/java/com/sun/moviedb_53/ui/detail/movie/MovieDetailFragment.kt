package com.sun.moviedb_53.ui.detail.movie

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment
import com.sun.moviedb_53.data.model.*
import com.sun.moviedb_53.data.source.repository.MovieRepository
import com.sun.moviedb_53.data.source.local.MovieLocalDataSource
import com.sun.moviedb_53.data.source.remote.MovieRemoteDataSource
import com.sun.moviedb_53.data.source.repository.FavoriteRepository
import com.sun.moviedb_53.extensions.addFragment
import com.sun.moviedb_53.extensions.loadFromUrl
import com.sun.moviedb_53.extensions.toGone
import com.sun.moviedb_53.extensions.toVisible
import com.sun.moviedb_53.ui.detail.actor.ActorFragment
import com.sun.moviedb_53.utils.Constant
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlin.math.roundToInt


@Suppress("DEPRECATION")
class MovieDetailFragment : BaseFragment(), MovieDetailContact.View {

    private var isFavoriteMovie = false
    private var favorite: Favorite? = null
    private var idMovie: Int? = null
    private var detailPresenter: MovieDetailPresenter? = null

    private val recommendationAdapter by lazy {
        RecommendationAdapter {
            addFragment(newInstance(it), R.id.mFrameMain)
        }
    }
    private val actorAdapter by lazy {
        ActorAdapter {
            addFragment(ActorFragment.newInstance(it), R.id.mFrameMain)
        }
    }

    override fun getLayoutId() = R.layout.fragment_movie_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailPresenter = MovieDetailPresenter(
            MovieRepository.getInstance(MovieRemoteDataSource.getInstance()),
            FavoriteRepository.getInstance(MovieLocalDataSource.getInstance(requireActivity()))
        )
        arguments?.let {
            idMovie = it.getInt(BUNDLE_ID_MOVIE_DETAIL)
        }
        detailPresenter?.let {
            it.setView(this)
            idMovie?.let { id ->
                it.getMovieDetail(id)
                it.getVideoTrailer(id)
                it.getListMovieRecommendations(id)
                it.getActorInMovieDetail(id)
            }
        }
    }

    override fun onViewCreated(view: View) {
        onInitView()
        onInitRecommend()
        onInitActor()
    }

    override fun loadContentMovieOnSuccess(movieDetail: MovieDetail) {
        movieDetail.run {
            favorite = Favorite(id, title, photoPoster, tagLine, rate)
        }
        initDataMovieDetail(movieDetail)
    }

    override fun loadVideoTrailerOnSuccess(video: VideoYoutube?) {
        imagePlay.setOnClickListener {
            video?.let {
                openYouTube(it.key)
            } ?: Toast.makeText(
                context,
                getString(R.string.no_video),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun loadListActorOnSuccess(actors: List<Actor>) {
        actorAdapter.setData(actors)
    }

    override fun loadRecommendationOnSuccess(movies: List<HotMovie>) {
        recommendationAdapter.setData(movies)
    }

    override fun onError(exception: Exception?) {
        exception?.let {
            Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onEvent() {
        imageBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        imageFavorite.setOnClickListener {
            favorite?.let {
                updateFavorite(it)
            }
        }
    }

    private fun onInitView() {
        val height = requireActivity().windowManager.defaultDisplay.run {
            val size = Point()
            getSize(size)
            size.y
        }

        constraintLayoutBar.apply {
            maxHeight = (height * 0.31).roundToInt()
            minHeight = (height * 0.31).roundToInt()
        }
    }

    private fun initDataMovieDetail(movieDetail: MovieDetail) {
        movieDetail.apply {
            if (title.isEmpty()) textTitle.toGone() else textTitle.toVisible()
            if (tagLine.isEmpty()) textTagLine.toGone() else textTagLine.toVisible()
            if (releaseDate.isEmpty()) textRelease.toGone() else textRelease.toVisible()

            textTitle.text = title
            textOverview.text = resources.getString(R.string.overview) + description
            textRelease.text = releaseDate
            textTagLine.text = tagLine
            textGenres.text = movieDetail.genres.joinToString(", ") { it.name }
            ratingBar.rating = rate.toFloat() / 2
            imagePoster.loadFromUrl(Constant.BASE_URL_IMAGE + photoPoster)
            imageBackground.loadFromUrl(Constant.BASE_URL_IMAGE + photoUrl)

            isFavoriteMovie = movieDetail.isFavorite
            selectedFavorite()
        }
    }

    private fun updateFavorite(favorite: Favorite) {
        detailPresenter?.let {
            if (isFavoriteMovie) it.deleteFavorite(favorite.id) else it.insertFavorite(favorite)
            isFavoriteMovie = !isFavoriteMovie
            selectedFavorite()
        }
    }

    private fun selectedFavorite() {
        if (isFavoriteMovie) imageFavorite.setImageResource(R.drawable.ic_heart_red)
        else imageFavorite.setImageResource(R.drawable.ic_heart_default)
    }

    private fun openYouTube(idYoutube: String) {
        try {
            context?.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(URI_YOUTUBE_APP + idYoutube)
                )
            )
        } catch (e: ActivityNotFoundException) {
            context?.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(URI_YOUTUBE_WEBSITE + idYoutube)
                )
            )
        }
    }

    private fun onInitRecommend() {
        recyclerViewRecommendations.apply {
            setHasFixedSize(true)
            adapter = recommendationAdapter
        }
    }

    private fun onInitActor() {
        recyclerViewActor.apply {
            setHasFixedSize(true)
            adapter = actorAdapter
        }
    }

    companion object {
        private const val BUNDLE_ID_MOVIE_DETAIL = "ID_MOVIE_DETAIL"
        private const val URI_YOUTUBE_APP = "vnd.youtube:"
        private const val URI_YOUTUBE_WEBSITE = "http://www.youtube.com/watch?v="

        fun newInstance(id: Int) = MovieDetailFragment().apply {
            arguments = bundleOf(BUNDLE_ID_MOVIE_DETAIL to id)
        }
    }
}
