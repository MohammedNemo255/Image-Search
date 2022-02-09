package com.nemodroid.searchimage.data_model

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class SplashImage {
    var id: String? = null
    var createdAt: String? = null
    var width: Int? = null
    var height: Int? = null
    var color: String? = null
    var blurHash: String? = null
    var likes: Int? = null
    var likedByUser: Boolean? = null
    var description: String? = null
    var user: User? = User()
    var currentUserCollections: ArrayList<String> = arrayListOf()
    var urls: Urls? = Urls()
    var links: Links? = Links()


    //region custom methods/binding adapters
    @BindingAdapter(value = ["url"], requireAll = false)
    fun setSplashImage(image: AppCompatImageView, url: String) {
        Glide.with(image.context).load(url).into(image)
    }
    //endregion
}