package com.example.gallery.extensions

import android.view.View

fun View.changeVisibilityState(state: Boolean) {
    if (state) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }
}