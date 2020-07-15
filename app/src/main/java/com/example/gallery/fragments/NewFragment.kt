package com.example.gallery.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.agrawalsuneet.dotsloader.loaders.LinearDotsLoader
import com.example.gallery.R
import com.example.gallery.adapter.ImagesAdapter
import com.example.gallery.model.ImageData


class NewFragment : Fragment() {

    private val adapter = ImagesAdapter(object : ImagesAdapter.Callback{
        override fun onImageClick(imageData: ImageData) {

            val imageInfoFragment = ImageInfoFragment()
            val args = Bundle()
            args.putString("imageName", imageData.name)
            args.putString("imageDescription", imageData.description)
            args.putString("imageFileName", imageData.image.name)
            imageInfoFragment.arguments = args

            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.addToBackStack(null)
                ?.replace(R.id.container, imageInfoFragment)
                ?.commit()
        }
    })

    private lateinit var loader: LinearDotsLoader
    private lateinit var placeHolder: View
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: NewViewModel
    private val presenter: NewPresenter get() = viewModel.presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewViewModel::class.java)
        subscribeState()
    }

    private fun subscribeState(){
        viewModel.items.observe(this, Observer { adapter.setItems(it) })
        viewModel.error.observe(this, Observer {
            placeHolder.visibility = when {
                it -> View.VISIBLE
                else -> View.GONE
            }
        })
        viewModel.isLoading.observe(this, Observer {
            val param = refreshLayout.layoutParams as MarginLayoutParams
            if (it) {
                param.setMargins(0,0,0,64)
                refreshLayout.layoutParams = param
                loader.visibility = View.VISIBLE
            } else {
                loader.visibility = View.GONE
                param.setMargins(0,0,0,0)
                refreshLayout.layoutParams = param
            }
        })

        viewModel.refreshing.observe(this, Observer { refreshLayout.isRefreshing = it })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        initRecyclerView(view)
        initRefreshLayout()

        presenter.loadImages()
    }

    private fun initViews(view: View) {
        placeHolder = view.findViewById(R.id.ll_no_inet)
        refreshLayout = view.findViewById(R.id.new_srl)
        loader = view.findViewById(R.id.loader_new)
    }

    private fun initRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.new_rv_images)
        recyclerView.adapter = adapter

        val layoutManager = recyclerView.layoutManager as GridLayoutManager

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastVisibleItemPosition() >= recyclerView.adapter!!.itemCount - 2
                    && presenter.loadMoreImages) {

                    presenter.loadImages()
                }
            }
        })

        val isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        layoutManager.spanCount = if (isPortrait) 2 else 3
    }

    private fun initRefreshLayout() {
        refreshLayout.setOnRefreshListener {
            presenter.onRefresh()
        }
    }
}