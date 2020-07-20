package com.example.gallery.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.gallery.R
import com.example.gallery.model.ImageData
import com.example.gallery.presenters.BasePresenter
import com.example.gallery.presenters.Inject
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.layout_place_holder.*

class PopularFragment : BaseFragment() {

    @Inject
    @InjectPresenter
    internal lateinit var presenter: BasePresenter


    @ProvidePresenter
    fun provide(): BasePresenter {
        return BasePresenter(isNew = false, isPopular = true)
    }

    override fun provideFragmentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initRefreshLayout()
        presenter.loadImages()
    }

    override fun initViews() {
        placeHolder = noInetLinearLayout
        refreshLayout = popularSwipeRefreshLayout
        loader = popularLoader
    }

    override fun initRecyclerView(items: ArrayList<ImageData>) {
        callbackAdapter(items)
        popularRecyclerView.adapter = adapter
        val layoutManager = popularRecyclerView.layoutManager as GridLayoutManager
        addOnScrollListenerRecyclerView(popularRecyclerView, layoutManager, presenter)
        changeSpanCount(layoutManager)
    }

    override fun onRefreshPresenter() {
        presenter.onRefresh()
    }
}