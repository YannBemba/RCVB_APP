package com.example.rcvb.onboarding.forms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rcvb.databinding.ActivityInscriptionBinding

class InscriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInscriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}