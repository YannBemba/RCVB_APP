package com.rcvb.rcvbapp.forms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.ActivityInscriptionBinding
import com.rcvb.rcvbapp.entites.Utilisateur
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.Exception

class InscriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInscriptionBinding

    private lateinit var mAuth: FirebaseAuth

    private val utilCollectionRef = Firebase.firestore.collection("utilisateurs")

    private lateinit var tilNomUtils: TextInputLayout
    private lateinit var tilPrenomUtils: TextInputLayout
    private lateinit var tilEmailUtils: TextInputLayout
    private lateinit var tilTelUtils: TextInputLayout
    private lateinit var tilMdpUtils: TextInputLayout

    private val TAG = "InscriptionActivity"

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
            saveUtils()
        }

    }
    private fun saveUtils(){

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

        if(tel.length != 10){
            tilTelUtils.error = "Veuillez entrer un numéro valide"
            tilTelUtils.requestFocus()
            return
        }

        if(mdp.isEmpty()){
            tilMdpUtils.error = "Le mdp est requis"
            tilMdpUtils.requestFocus()
            return
        }

        if(mdp.length < 6) {
            tilMdpUtils.error = "Le mot de passe doit contenir plus de 6 caractères"
            tilMdpUtils.requestFocus()
            return
        }
        // Insertion d'un utilisateur
        val util = Utilisateur(0, nom, prenom, email, tel, mdp)
        createUtil(util)
        firebaseCreateUser(email, mdp)

    }

    private fun firebaseCreateUser(email: String, mdp: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                mAuth.createUserWithEmailAndPassword(email, mdp).await()
                withContext(Dispatchers.Main)  {
                    checkLoggedInState()
                }
            } catch(e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@InscriptionActivity, e.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun checkLoggedInState() {
        if(mAuth.currentUser == null) {
            Toast.makeText(this, "Identifiant et/ou mot de passe incorrect", Toast.LENGTH_LONG)
                .show()
        } else {
            val intent = Intent(this@InscriptionActivity, ConnexionActivity::class.java)
            resetInput()
            startActivity(intent)
        }
    }

    private fun createUtil(util: Utilisateur) = CoroutineScope(Dispatchers.IO).launch {
        try {
            utilCollectionRef.add(util).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(this@InscriptionActivity, "Connexion réussie", Toast.LENGTH_LONG)
                    .show()
            }
            Log.d(TAG, "Utilisateur ajouté à Firestore")
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@InscriptionActivity, e.message, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun resetInput() {
        tilNomUtils.editText?.text!!.isEmpty()
        tilPrenomUtils.editText?.text!!.isEmpty()
        tilTelUtils.editText?.text!!.isEmpty()
        tilEmailUtils.editText?.text!!.isEmpty()
        tilMdpUtils.editText?.text!!.isEmpty()
    }

}