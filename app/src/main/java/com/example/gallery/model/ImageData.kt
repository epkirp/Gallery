package com.example.gallery.model

import java.io.Serializable

data class ImageData(
    val id: Int,
    val name: String,
    val dateCreate: String,
    val description: String,
    val new: Boolean,
    val popular: Boolean,
    val image: Image,
    val user: String?
) : Serializable