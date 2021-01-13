package com.example.rcvb.onboarding.forms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rcvb.RCVBAppActivity
import com.example.rcvb.databinding.ActivityInscriptionBinding

class InscriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInscriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInscription.setOnClickListener {
            val intent = Intent(this, RCVBAppActivity::class.java)
            startActivity(intent)
        }

    }


}