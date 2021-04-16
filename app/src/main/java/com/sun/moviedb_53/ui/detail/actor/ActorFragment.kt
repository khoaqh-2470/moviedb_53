package com.sun.moviedb_53.ui.detail.actor

import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment
import com.sun.moviedb_53.data.model.ActorDetail
import com.sun.moviedb_53.data.model.External

class ActorFragment : BaseFragment(), ActorContact.View {

    override fun getLayoutId() = R.layout.fragment_actor

    override fun onViewCreated(view: View) {
        arguments?.let {
            it.getInt(BUNDLE_ID_ACTOR_DETAIL)
        }
    }

    override fun loadContentActorOnSuccess(actorDetail: ActorDetail) {}

    override fun loadContentExternalOnSuccess(external: External) {}

    override fun onError(exception: Exception?) {
        exception?.let { Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show() }
    }

    companion object {
        private const val BUNDLE_ID_ACTOR_DETAIL = "BUNDLE_ID_ACTOR_DETAIL"

        fun newInstance(id: Int) = ActorFragment().apply {
            arguments = bundleOf(BUNDLE_ID_ACTOR_DETAIL to id)
        }
    }
}
