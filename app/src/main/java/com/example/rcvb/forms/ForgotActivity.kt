package com.example.rcvb.forms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.rcvb.databinding.ActivityForgotBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotBinding

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        mdpOublieModif().setOnClickListener {
            setMdp()
        }

    }

    private fun setMdp() {
        val email = etEmailEdit().text.toString().trim()

        if (email.isEmpty()){
            this.etEmailEdit().error = "L'email est requis"
            this.etEmailEdit().requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.etEmailEdit().error = "L'email doit être valide"
            this.etEmailEdit().requestFocus()
            return
        }

        this.getProgressBar().visibility = View.VISIBLE
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this,
                    "Vérifier votre email pour modifier votre mot de passe",
                    Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(this,
                    "Essaie encore !",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun etEmailEdit(): EditText {
        return binding.etEmail
    }

    private fun mdpOublieModif(): Button {
        return binding.btnMdpOublie
    }

    private fun getProgressBar(): ProgressBar {
        return binding.progressBar2
    }

}