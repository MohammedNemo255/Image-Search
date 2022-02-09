package com.nemodroid.searchimage.data_model

import java.io.Serializable

class User : Serializable {
    var id: String? = null
    var username: String? = null
    var name: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var instagramUsername: String? = null
    var twitterUsername: String? = null
    var portfolioUrl: String? = null
    var profileImage: ProfileImage? = ProfileImage()
    var links: Links? = Links()
}