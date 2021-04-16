package com.sun.moviedb_53.ui.detail.actor

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.Spanned
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment
import com.sun.moviedb_53.data.model.ActorDetail
import com.sun.moviedb_53.data.model.External
import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.data.source.remote.MovieRemoteDataSource
import com.sun.moviedb_53.data.source.repository.MovieRepository
import com.sun.moviedb_53.extensions.addFragment
import com.sun.moviedb_53.extensions.loadFromUrl
import com.sun.moviedb_53.extensions.toGone
import com.sun.moviedb_53.extensions.toVisible
import com.sun.moviedb_53.ui.detail.movie.MovieDetailFragment
import com.sun.moviedb_53.ui.detail.movie.RecommendationAdapter
import com.sun.moviedb_53.utils.Constant
import com.sun.moviedb_53.utils.Gender
import kotlinx.android.synthetic.main.fragment_actor.*
import kotlinx.android.synthetic.main.fragment_actor.imageBack


class ActorFragment : BaseFragment(), ActorContact.View {

    private lateinit var actorDetailPresenter: ActorDetailPresenter
    private var idActor: Int? = null

    private val movieAdapter by lazy {
        RecommendationAdapter {
            addFragment(MovieDetailFragment.newInstance(it), R.id.mFrameMain)
        }
    }

    override fun getLayoutId() = R.layout.fragment_actor

    override fun onViewCreated(view: View) {
        onInitMovies()

        actorDetailPresenter = ActorDetailPresenter(
            MovieRepository.getInstance(MovieRemoteDataSource.getInstance())
        )
        arguments?.let {
            idActor = it.getInt(BUNDLE_ID_ACTOR_DETAIL)
        }
        actorDetailPresenter.run {
            idActor?.let {
                setView(this@ActorFragment)
                getActorDetail(it)
                getExternal(it)
                getListMovieOfActor(it)
            }
        }
    }

    override fun onEvent() {
        imageBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun loadContentActorOnSuccess(actorDetail: ActorDetail) {
        initDataActorDetail(actorDetail)
    }

    @SuppressLint("SetTextI18n")
    private fun initDataActorDetail(actorDetail: ActorDetail) {
        resources.apply {
            actorDetail.run {
                name?.let { textNameActor.text = it }
                birthday?.let {
                    textBirthday.text = formatHTML(getString(R.string.birthday), it)
                }
                address?.let {
                    textAddress.text = formatHTML(getString(R.string.place_of_birth), it)
                }
                biography?.let {
                    textBiography.text = formatHTML(getString(R.string.biography), it)
                }
                gender?.let {
                    textGender.text =
                        formatHTML(getString(R.string.gender), Gender.values()[it].name)
                }
                imageUrl?.let { imagePosterActor.loadFromUrl(Constant.BASE_URL_IMAGE + it) }
            }
        }
    }

    override fun loadContentExternalOnSuccess(external: External) {
        external.run {
            facebook?.let {
                if (it != "")
                    imageFacebook.toVisible()
                else
                    imageFacebook.toGone()
            } ?: imageFacebook.toGone()
            twitter?.let {
                if (it != "")
                    imageTwitter.toVisible()
                else
                    imageTwitter.toGone()
            } ?: imageTwitter.toGone()
            instagram?.let {
                if (it != "")
                    imageInstagram.toVisible()
                else
                    imageInstagram.toGone()
            } ?: imageInstagram.toGone()

            imageFacebook.setOnClickListener {
                facebook?.let { it1 -> openExternal(Constant.URL_FACEBOOK + it1) }
            }
            imageTwitter.setOnClickListener {
                twitter?.let { it1 -> openExternal(Constant.URL_TWITTER + it1) }
            }
            imageInstagram.setOnClickListener {
                instagram?.let { it1 -> openExternal(Constant.URL_INSTAGRAM + it1) }
            }
        }
    }

    override fun loadMoviesOnSuccess(movies: List<HotMovie>) {
        movieAdapter.setData(movies)
    }

    override fun onError(exception: Exception?) {
        exception?.let {
            Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onInitMovies() {
        recyclerViewKnownFor.apply {
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun openExternal(s: String) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(s))
            startActivity(browserIntent)
        } catch (exception: Exception) {
            onError(exception)
        }
    }

    private fun formatHTML(s: String, s1: String): Spanned {
        return HtmlCompat.fromHtml("<B>$s</B><BR> $s1", HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    companion object {
        private const val BUNDLE_ID_ACTOR_DETAIL = "BUNDLE_ID_ACTOR_DETAIL"

        fun newInstance(id: Int) = ActorFragment().apply {
            arguments = bundleOf(BUNDLE_ID_ACTOR_DETAIL to id)
        }
    }
}
