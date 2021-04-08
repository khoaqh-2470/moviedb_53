package com.sun.moviedb_53.ui.homepage

import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseFragment
import com.sun.moviedb_53.ui.favourite.FavouriteFragment
import com.sun.moviedb_53.ui.genres.GenresFragment
import com.sun.moviedb_53.ui.hot.HotFragment
import com.sun.moviedb_53.ui.search.SearchFragment
import kotlinx.android.synthetic.main.fragment_home_page.*

class HomePageFragment : BaseFragment() {
    override fun getLayoutId() = R.layout.fragment_home_page

    override fun onViewCreated(view: View) {
        onInitViewPager()
        onInitNav()
        onClick()
    }

    private fun onClick() {
        edtSearch.setOnClickListener {
            parentActivity?.apply {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mFrameMain, SearchFragment()).commit()
            }
        }
    }

    private fun onInitNav() {
        bottomNavHomePage.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.hotPage -> {
                    viewPagerHomePage.currentItem = 0
                    true
                }
                R.id.genresPage -> {
                    viewPagerHomePage.currentItem = 1
                    true
                }
                R.id.favoritePage -> {
                    viewPagerHomePage.currentItem = 2
                    true
                }
                else -> false
            }
        }
    }

    private fun onInitViewPager() {
        val listFragment = listOf<Fragment>(HotFragment(), GenresFragment(), FavouriteFragment())
        fragmentManager?.let {
            viewPagerHomePage.adapter = HomePageAdapter(it, listFragment)
        }
    }
}
