package com.example.rcvb.forms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.example.rcvb.databinding.ActivityInscriptionBinding
import com.google.firebase.auth.FirebaseAuth

class InscriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInscriptionBinding
    private lateinit var mAuth: FirebaseAuth

    private lateinit var etNomUtils: EditText
    private lateinit var etPrenomUtils: EditText
    private lateinit var etEmailUtils: EditText
    private lateinit var etTelUtils: EditText
    private lateinit var etMdpUtils: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        etNomUtils = binding.etNom
        etPrenomUtils = binding.etPrenom
        etEmailUtils = binding.etEmail
        etTelUtils = binding.etTel
        etMdpUtils = binding.etMdp

        binding.btnInscription.setOnClickListener {
            enregistrerUtils()
        }

    }

    private fun enregistrerUtils(){
        val nom = binding.etNom.text.toString().trim()
        val prenom = binding.etPrenom.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val tel = binding.etTel.text.toString().trim()
        val mdp = binding.etMdp.text.toString().trim()

        val progressBar = binding.progressBarInscription

        if(nom.isEmpty()){
            etNomUtils.error = "Le nom est requis"
            etNomUtils.requestFocus()
            return
        }

        if(prenom.isEmpty()){
            etPrenomUtils.error = "Le prénom est requis"
            etPrenomUtils.requestFocus()
            return
        }

        if(email.isEmpty()){
            etEmailUtils.error = "L'email est requis"
            etEmailUtils.requestFocus()
            return
        }

        // Renforcer la validation de l'email

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmailUtils.error = "Veillez saisir un email valide"
            etEmailUtils.requestFocus()
            return
        }

        if(tel.isEmpty()){
            etTelUtils.error = "Le numéro de téléphone est requis"
            etTelUtils.requestFocus()
            return
        }

        if(mdp.isEmpty())
        {
            etMdpUtils.error = "Le mdp est requis"
            etMdpUtils.requestFocus()
        }

        if(mdp.length < 6) {
            etMdpUtils.error = "Votre mot de passe doit pas dépasser 6 caractères"
            etMdpUtils.requestFocus()
            return
        }

        Toast.makeText(applicationContext, "Bienvenue $prenom", Toast.LENGTH_LONG).show()
    }


}