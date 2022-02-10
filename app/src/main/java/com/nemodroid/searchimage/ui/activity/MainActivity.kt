package com.nemodroid.searchimage.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nemodroid.searchimage.R
import com.nemodroid.searchimage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}