package com.example.movielike.ui.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movielike.databinding.ActivityFragmentBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

