package com.rcvb.rcvbapp

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rcvb.rcvbapp.databinding.ActivityRcvbappBinding

class RCVBAppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRcvbappBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRcvbappBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigationView
        val navController = Navigation.findNavController(this, R.id.fragment_nav)

        bottomNavigationView.setupWithNavController(navController)

    }

}