package com.example.gallery.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gallery.model.ImageData

class NewViewModel(app: Application): AndroidViewModel(app) {

    val presenter = NewPresenter(this)
    val items = MutableLiveData<List<ImageData>>(ArrayList())
    val error = MutableLiveData<Boolean>()
    val refreshing = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
}
