package com.example.gallery.fragments

import com.example.gallery.model.GalleryPart
import com.example.gallery.model.ImageData
import com.example.gallery.retrofit.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class NewPresenter(private val viewModel: NewViewModel) {

    private var currentPage = 1
    var loadMoreImages = true

    fun onRefresh() {
        loadMoreImages = true
        currentPage = 1
        viewModel.items.value = ArrayList()
        loadImages()
    }

    fun loadImages(){
        viewModel.isLoading.value = true
        RetrofitApi.galleryApi.getNewImages(currentPage).enqueue(object : Callback<GalleryPart?> {

            override fun onFailure(call: Call<GalleryPart?>, t: Throwable) {
                viewModel.items.value = ArrayList()
                viewModel.error.value = true
                viewModel.refreshing.value = false
                viewModel.isLoading.value = false
            }

            override fun onResponse(call: Call<GalleryPart?>, response: Response<GalleryPart?>) {
                val part = response.body()

                if (response.isSuccessful && part != null) {

                    if (currentPage == part.countOfPages) loadMoreImages = false

                    val items = ArrayList<ImageData>()
                    if (currentPage > 1) {
                        items.addAll(viewModel.items.value!!)
                    }
                    items.addAll(part.data)
                    viewModel.items.value = items
                    viewModel.error.value = false
                    currentPage++
                } else {
                    viewModel.error.value = true
                }

                viewModel.refreshing.value = false
                viewModel.isLoading.value = false
            }

        })
    }
}