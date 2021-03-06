package com.rcvb.rcvbapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rcvb.rcvbapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        val utils = mAuth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if(utils != null){
                val homeIntent = Intent(this, RCVBAppActivity::class.java)
                startActivity(homeIntent)
                finish()
            }
        }, 3000)

    }


}