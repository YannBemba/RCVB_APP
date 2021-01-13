package com.example.rcvb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rcvb.databinding.ActivityRcvbappBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class RCVBAppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRcvbappBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRcvbappBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.fragment_nav)

        bottomNavigationView.setupWithNavController(navController)

    }
}