package com.nemodroid.searchimage.data_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Links : Serializable {
    var self: String? = null
    var html: String? = null
    var download: String? = null
}