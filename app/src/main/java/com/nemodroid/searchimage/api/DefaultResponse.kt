package com.nemodroid.searchimage.api

import com.nemodroid.searchimage.data_model.UnSplashImage

data class DefaultResponse(val total: Int, val totalPage: Int, var results: List<UnSplashImage>)