package com.example.gallery.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gallery.R.layout
import com.example.gallery.retrofit.RetrofitApi
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image_info.*
import kotlinx.android.synthetic.main.fragment_image_info.view.*


class ImageInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout.fragment_image_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        infoToolbar.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        val bundle: Bundle? = this.arguments

        Picasso.get().load(RetrofitApi.MEDIA_URL + (bundle?.getString("imageFileName"))).fit()
            .into(view.infoImageView)

        imageInfoTextDescription.text = bundle?.getString("imageDescription")
        imageInfoTextName.text = bundle?.getString("imageName")

    }
}