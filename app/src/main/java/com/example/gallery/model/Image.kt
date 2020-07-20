package com.example.gallery.model

import java.io.Serializable

data class Image(
    val id: Long,
    val name: String
) : Serializable