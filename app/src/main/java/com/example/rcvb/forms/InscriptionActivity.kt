package com.example.rcvb.forms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import com.example.rcvb.R
import com.example.rcvb.databinding.ActivityInscriptionBinding
import com.example.rcvb.entites.Utilisateur
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import www.sanju.motiontoast.MotionToast

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

        val btnInscription = binding.btnInscription

        btnInscription.setOnClickListener {
            enregistrerUtils()
        }

    }

    private fun enregistrerUtils(){
        val nom = binding.etNom.text.toString().trim()
        val prenom = binding.etPrenom.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val tel = binding.etTel.text.toString().trim()
        val mdp = binding.etMdp.text.toString().trim()

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
            etMdpUtils.error = "Le mot de passe doit contenir plus de 6 caractères"
            etMdpUtils.requestFocus()
            return
        }

        firebaseCreateUser(email, mdp, nom, prenom, tel)

    }

    private fun firebaseCreateUser(email: String, mdp: String, nom: String, prenom: String, tel: String) {

        mAuth.createUserWithEmailAndPassword(email, mdp)
                .addOnCompleteListener(OnCompleteListener { tache ->
                    if(tache.isSuccessful){
                        val utils = Utilisateur(nom, prenom, email, tel, mdp)

                        FirebaseDatabase.getInstance().getReference("Utilisateur")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .setValue(utils)
                            .addOnCompleteListener(OnCompleteListener { tache2 ->
                                if(tache2.isSuccessful) {
                                    MotionToast.Companion.darkColorToast(
                                        this,
                                        "Inscription réussie",
                                        "Votre compte a bien été crée $prenom",
                                        MotionToast.TOAST_SUCCESS,
                                        MotionToast.GRAVITY_BOTTOM,
                                        MotionToast.LONG_DURATION,
                                        ResourcesCompat.getFont(this, R.font.poppins_bold)
                                    )
                                    startActivity(Intent(this@InscriptionActivity, ConnexionActivity::class.java))

                                    // Redirectionner à la page Connexion
                                } else {

                                    /*val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)
                                    snackbar.show()*/

                                }
                            })
                    } else {
                        MotionToast.Companion.darkColorToast(
                            this,
                            "Erreur de connexion",
                            "",
                            MotionToast.TOAST_SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(this, R.font.poppins_bold)
                        )
                    }
                })
    }


}