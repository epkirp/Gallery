package com.example.gallery.retrofit

import com.example.gallery.model.GalleryPart
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GalleryApi {
    @GET("/api/photos/")
    fun getNewImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int = RetrofitApi.LIMIT_PAGE,
        @Query("new") new: Boolean = true
    ): Call<GalleryPart?>

    @GET("/api/photos/")
    fun getPopularImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int = RetrofitApi.LIMIT_PAGE,
        @Query("popular") popular: Boolean = true
    ): Call<GalleryPart?>
}