package com.rcvb.rcvbapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.rcvb.rcvbapp.databinding.ActivitySuccessDialogBinding

class SuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuccessDialogBinding

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        val utils = mAuth.currentUser

        Handler().postDelayed({
            if(utils != null) {
                startActivity(Intent(this, RCVBAppActivity::class.java))
                finish()
            }
        },3000)

    }
}