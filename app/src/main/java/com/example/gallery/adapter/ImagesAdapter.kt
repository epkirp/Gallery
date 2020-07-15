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
    private var callback: Callback
): RecyclerView.Adapter<ImagesAdapter.ImageHolder>() {

    interface Callback {
        fun onImageClick(image: ImageData)
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    private val images = ArrayList<ImageData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_image, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.onBind(images[position])
    }

    fun addItems(items: List<ImageData>) {
        this.images.addAll(items)
        notifyItemRangeInserted(itemCount - items.size, items.size)
    }

    fun setItems(items: List<ImageData>) {
        this.images.clear()
        this.images.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems(){
        images.clear()
        notifyDataSetChanged()
    }

    inner class ImageHolder(val view: View): RecyclerView.ViewHolder(view){

        init {
            view.item_iv_image.setOnClickListener {
                callback.onImageClick(images[adapterPosition])
            }
        }

        fun onBind(item: ImageData) {
            Picasso.get().load(RetrofitApi.MEDIA_URL + item.image.name).fit().
            into(view.item_iv_image, object : com.squareup.picasso.Callback {

                override fun onSuccess() {
                    if (view.progressBar != null) {
                        view.progressBar.visibility = View.GONE;
                    }
                }

                override fun onError(e: Exception?) {
                }
            })
        }
    }

}