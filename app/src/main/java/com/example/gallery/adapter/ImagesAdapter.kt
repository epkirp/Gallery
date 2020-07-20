package com.example.gallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R
import com.example.gallery.model.ImageData
import com.example.gallery.retrofit.RetrofitApi
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*
import java.lang.Exception


class ImagesAdapter(
    private var images: ArrayList<ImageData>,
    private var callback: Callback
) : RecyclerView.Adapter<ImagesAdapter.ImageHolder>() {

    interface Callback {
        fun onImageClick(image: ImageData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_image, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.onBind(images[position])
    }

    inner class ImageHolder(val view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.itemImageView.setOnClickListener {
                callback.onImageClick(images[adapterPosition])
            }
        }

        fun onBind(item: ImageData) {
            Picasso.get().load(RetrofitApi.MEDIA_URL + item.image.name).fit()
                .into(view.itemImageView, object : com.squareup.picasso.Callback {

                    override fun onSuccess() {
                        if (view.progressBar != null) {
                            view.progressBar.visibility = View.GONE
                        }
                    }

                    override fun onError(e: Exception?) {
                    }
                })
        }
    }

}