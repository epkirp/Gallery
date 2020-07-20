package com.example.gallery.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.gallery.BaseView
import com.example.gallery.model.GalleryPart
import com.example.gallery.model.ImageData
import com.example.gallery.retrofit.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InjectViewState
class BasePresenter @Inject constructor(
    private val isNew: Boolean,
    private val isPopular: Boolean
) : MvpPresenter<BaseView>() {

    private var currentPage = 1
    private var isFirstLoad = true
    var loadMoreImages = true
    private var items = ArrayList<ImageData>()

    fun onRefresh() {
        viewState.changeRefreshVisibilityState(true)

        loadMoreImages = true
        currentPage = 1
        items.clear()
        loadImages()

        viewState.changeRefreshVisibilityState(false)
    }

    fun loadImages() {
        viewState.changeLoaderVisibilityState(true)
        RetrofitApi.galleryApi.getImages(currentPage, new = isNew, popular = isPopular)
            .enqueue(object : Callback<GalleryPart?> {

                override fun onFailure(call: Call<GalleryPart?>, t: Throwable) {
                    onFailureLoad()
                }

                override fun onResponse(
                    call: Call<GalleryPart?>,
                    response: Response<GalleryPart?>
                ) {
                    onResponseLoad(response)
                }
            })
    }

    private fun onFailureLoad() {
        items.clear()

        viewState.changeErrorVisibilityState(true)
        viewState.changeRefreshVisibilityState(false)
        viewState.changeLoaderVisibilityState(false)
        if (isFirstLoad) {
            isFirstLoad = false
        }
    }

    private fun onResponseLoad(response: Response<GalleryPart?>) {
        val part = response.body()

        if (response.isSuccessful && part != null) {

            if (currentPage == part.countOfPages) {
                loadMoreImages = false
            }

            items.addAll(part.data)
            if (isFirstLoad) {
                viewState.initRecyclerView(items)
                isFirstLoad = false
            } else {
                viewState.updateAdapter()
            }

            viewState.changeErrorVisibilityState(false)
            currentPage++
        } else {
            items.clear()
            viewState.changeErrorVisibilityState(true)
        }

        viewState.changeRefreshVisibilityState(false)
        viewState.changeLoaderVisibilityState(false)
    }
}

annotation class Inject
