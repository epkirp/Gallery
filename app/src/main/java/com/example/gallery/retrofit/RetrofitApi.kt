package com.example.gallery.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {
    val galleryApi: GalleryApi by lazy(LazyThreadSafetyMode.NONE) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GalleryApi::class.java)
    }

    private const val BASE_URL = "http://gallery.dev.webant.ru/"
    const val MEDIA_URL = "http://gallery.dev.webant.ru/media/"
    const val LIMIT_PAGE = 10
}