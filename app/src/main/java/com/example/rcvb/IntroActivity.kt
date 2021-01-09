package com.example.rcvb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class IntroActivity : AppCompatActivity() {

    private var TIME = 4000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@IntroActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME)

    }
}