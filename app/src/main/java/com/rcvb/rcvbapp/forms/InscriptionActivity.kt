package com.rcvb.rcvbapp.forms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.ActivityInscriptionBinding
import com.rcvb.rcvbapp.entites.Utilisateur
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import www.sanju.motiontoast.MotionToast

class InscriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInscriptionBinding
    private lateinit var mAuth: FirebaseAuth

    private lateinit var tilNomUtils: TextInputLayout
    private lateinit var tilPrenomUtils: TextInputLayout
    private lateinit var tilEmailUtils: TextInputLayout
    private lateinit var tilTelUtils: TextInputLayout
    private lateinit var tilMdpUtils: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        tilNomUtils = binding.tilNom
        tilPrenomUtils = binding.tilPrenom
        tilEmailUtils = binding.tilEmail
        tilTelUtils = binding.tilTel
        tilMdpUtils = binding.tilMdp

        val btnInscription = binding.btnInscription

        btnInscription.setOnClickListener {
            enregistrerUtils()
        }

    }

    private fun enregistrerUtils(){

        val nom = binding.tilNom.editText?.text.toString().trim()
        val prenom = binding.tilPrenom.editText?.text.toString().trim()
        val email = binding.tilEmail.editText?.text.toString().trim()
        val tel = binding.tilTel.editText?.text.toString().trim()
        val mdp = binding.tilMdp.editText?.text.toString().trim()

        if(nom.isEmpty()){
            tilNomUtils.error = "Le nom est requis"
            tilNomUtils.requestFocus()
            return
        }

        if(prenom.isEmpty()){
            tilPrenomUtils.error = "Le prénom est requis"
            tilPrenomUtils.requestFocus()
            return
        }

        if(email.isEmpty()){
            tilEmailUtils.error = "L'email est requis"
            tilEmailUtils.requestFocus()
            return
        }

        // Renforcer la validation de l'email

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmailUtils.error = "Veuillez saisir un email valide"
            tilEmailUtils.requestFocus()
            return
        }

        if(tel.isEmpty()){
            tilTelUtils.error = "Le numéro de téléphone est requis"
            tilTelUtils.requestFocus()
            return
        }

        if(mdp.isEmpty()){
            tilMdpUtils.error = "Le mdp est requis"
            tilMdpUtils.requestFocus()
        }

        if(mdp.length < 6) {
            tilMdpUtils.error = "Le mot de passe doit contenir plus de 6 caractères"
            tilMdpUtils.requestFocus()
            return
        }

        firebaseCreateUser(email, mdp)

    }

    private fun firebaseCreateUser(email: String, mdp: String) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                mAuth.createUserWithEmailAndPassword(email, mdp)
                withContext(Dispatchers.Main) {
                    checkUser()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let {
                        MotionToast.Companion.darkColorToast(
                                this@InscriptionActivity,
                                "Erreur de connexion",
                                it,
                                MotionToast.TOAST_SUCCESS,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(this@InscriptionActivity, R.font.poppins_bold)
                        )
                    }
                }
            }
        }

    }

    private fun checkUser() {
        val user = mAuth.currentUser

        if(user == null) {
            MotionToast.Companion.darkColorToast(
                    this@InscriptionActivity,
                    "Erreur de connexion",
                    "",
                    MotionToast.TOAST_SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this@InscriptionActivity, R.font.poppins_bold)
            )
        } else {
            MotionToast.Companion.darkColorToast(
                    this,
                    "Inscription réussie",
                    "Votre compte a bien été crée",
                    MotionToast.TOAST_SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this@InscriptionActivity, R.font.poppins_bold)
            )
            startActivity(Intent(this@InscriptionActivity, ConnexionActivity::class.java))
        }

    }

}