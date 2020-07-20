package com.example.gallery.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.agrawalsuneet.dotsloader.loaders.LinearDotsLoader
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.gallery.BaseView
import com.example.gallery.R
import com.example.gallery.adapter.ImagesAdapter
import com.example.gallery.extensions.changeVisibilityState
import com.example.gallery.model.ImageData
import com.example.gallery.presenters.BasePresenter

abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    protected lateinit var adapter: ImagesAdapter
    protected lateinit var loader: LinearDotsLoader
    protected lateinit var placeHolder: View
    protected lateinit var refreshLayout: SwipeRefreshLayout

    abstract fun provideFragmentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?

    abstract fun initViews()

    abstract fun onRefreshPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return provideFragmentView(inflater, container, savedInstanceState)
    }

    override fun updateAdapter() {
        adapter.notifyDataSetChanged()
    }

    override fun changeLoaderVisibilityState(state: Boolean) {
        val param = refreshLayout.layoutParams as ViewGroup.MarginLayoutParams
        if (state) {
            param.setMargins(0, 0, 0, 64)
        } else {
            param.setMargins(0, 0, 0, 0)
        }
        refreshLayout.layoutParams = param
        loader.changeVisibilityState(state)
    }

    override fun changeErrorVisibilityState(state: Boolean) {
        placeHolder.changeVisibilityState(state)
    }

    override fun changeRefreshVisibilityState(state: Boolean) {
        refreshLayout.isRefreshing = state
    }

    protected fun initRefreshLayout() {
        refreshLayout.setOnRefreshListener {
            onRefreshPresenter()
        }
    }

    protected fun callbackAdapter(items: ArrayList<ImageData>) {
        adapter = ImagesAdapter(items, object : ImagesAdapter.Callback {
            override fun onImageClick(image: ImageData) {

                val imageInfoFragment = ImageInfoFragment()
                val args = Bundle()
                args.putString("imageName", image.name)
                args.putString("imageDescription", image.description)
                args.putString("imageFileName", image.image.name)
                imageInfoFragment.arguments = args

                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.addToBackStack(null)
                    ?.replace(R.id.container, imageInfoFragment)
                    ?.commit()
            }
        })
    }

    protected fun addOnScrollListenerRecyclerView(
        recyclerView: RecyclerView,
        layoutManager: GridLayoutManager,
        presenter: BasePresenter
    ) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastVisibleItemPosition() >= recyclerView.adapter!!.itemCount - 2
                    && presenter.loadMoreImages
                ) {
                    presenter.loadImages()
                }
            }
        })
    }

    protected fun changeSpanCount(layoutManager: GridLayoutManager) {
        val isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        layoutManager.spanCount = if (isPortrait) 2 else 3
    }
}
