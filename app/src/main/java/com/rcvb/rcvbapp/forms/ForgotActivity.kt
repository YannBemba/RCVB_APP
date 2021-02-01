package com.rcvb.rcvbapp.forms

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.ActivityForgotBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

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
        val email = etEmailEdit().editText?.text.toString().trim()

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

        sendEmailVerification(email)

    }

    private fun sendEmailVerification(email: String) {

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if(task.isSuccessful){

                MaterialAlertDialogBuilder(this)
                        .setIcon(R.drawable.ic_email_check)
                        .setTitle("Modification du mot de passe")
                        .setMessage("Vérifier votre email pour modifier votre mot de passe")
                        .setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->

                        })
                        .show()

            } else {
                Toast.makeText(this,
                        "Essaie encore !",
                        Toast.LENGTH_LONG)
                        .show()
            }
        }
    }

    private fun etEmailEdit(): TextInputLayout {
        return binding.tilEmailOublie
    }

    private fun mdpOublieModif(): Button {
        return binding.btnMdpOublie
    }

}