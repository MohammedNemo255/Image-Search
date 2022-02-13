package com.nemodroid.searchimage.ui.fragment.main

import androidx.lifecycle.ViewModel
import com.nemodroid.searchimage.repository.UnSplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UnSplashRepository) :
    ViewModel() {
}