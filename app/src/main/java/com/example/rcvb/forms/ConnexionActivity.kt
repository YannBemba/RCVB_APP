package com.example.rcvb.forms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rcvb.databinding.ActivityConnexionBinding

class ConnexionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConnexionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnexionBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}