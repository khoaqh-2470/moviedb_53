package com.sun.moviedb_53.ui.detail.actor

import android.view.View
import androidx.core.os.bundleOf
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment

class ActorFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_actor

    override fun onViewCreated(view: View) {
        arguments?.let {
            it.getInt(BUNDLE_ID_ACTOR_DETAIL)
        }?.let {

        }
    }

    companion object {
        private const val BUNDLE_ID_ACTOR_DETAIL = "BUNDLE_ID_ACTOR_DETAIL"

        fun newInstance(id: Int) = ActorFragment().apply {
            arguments = bundleOf(BUNDLE_ID_ACTOR_DETAIL to id)
        }
    }
}
