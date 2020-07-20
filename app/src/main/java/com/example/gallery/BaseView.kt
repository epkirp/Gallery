package com.example.gallery

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.gallery.model.ImageData

interface BaseView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateAdapter()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun initRecyclerView(items: ArrayList<ImageData>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeLoaderVisibilityState(state: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeErrorVisibilityState(state: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeRefreshVisibilityState(state: Boolean)
}