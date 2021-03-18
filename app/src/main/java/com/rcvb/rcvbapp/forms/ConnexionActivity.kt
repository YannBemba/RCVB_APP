package com.rcvb.rcvbapp.forms


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.RCVBAppActivity
import com.rcvb.rcvbapp.databinding.ActivityConnexionBinding
import com.rcvb.rcvbapp.databinding.CustomToastBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class ConnexionActivity: AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 23
    }

    private lateinit var binding: ActivityConnexionBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val TAG = "ConnexionActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnexionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        val btnConnexion = binding.btnConnexion
        val btnGoogle = binding.btnGoogle

        val tvInscription = binding.tvInscription
        val mdpOublie = binding.tvMdpOublie

        btnConnexion.setOnClickListener {
            this.userLogin()
        }

        btnGoogle.setOnClickListener {
            this.googleAuth()
        }

        tvInscription.setOnClickListener {
            startActivity(Intent(this, InscriptionActivity::class.java))
        }

        mdpOublie.setOnClickListener {
            startActivity(Intent(this, ForgotActivity::class.java))
        }

    }

    private fun getEmailEdit(): TextInputLayout {
        return binding.tilEmailConnexion
    }

    private fun getMdpEdit(): TextInputLayout {
        return binding.tilMdpConnexion
    }

    private fun userLogin() {

        val email = this.getEmailEdit().editText?.text.toString().trim()
        val mdp = this.getMdpEdit().editText?.text.toString().trim()

        if(email.isEmpty()) {
            this.getEmailEdit().error = "L'email est requis"
            this.getEmailEdit().requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.getEmailEdit().error = "Veuillez entrer un email valide"
            this.getEmailEdit().requestFocus()
            return
        }

        if(mdp.isEmpty()) {
            this.getMdpEdit().error = "Le mot de passe est requis"
            this.getMdpEdit().requestFocus()
            return
        }

        if(mdp.length < 6) {
            this.getMdpEdit().error = "Le mot de passe doit contenir plus de 6 caractères"
        }

        connecterUtils(email, mdp)

    }

    private fun connecterUtils(email: String, mdp: String) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                mAuth.signInWithEmailAndPassword(email, mdp).await()
                withContext(Dispatchers.Main) {
                    checkUser()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ConnexionActivity, "Identifiant et/ou mot de passe incorrect", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }

    private fun checkUser() {
        if(mAuth.currentUser == null) {
            Toast.makeText(this@ConnexionActivity, "Identifiant et/ou mot de passe incorrect", Toast.LENGTH_LONG)
                .show()
        } else {
            sendEmailVerification()
        }
    }

    private fun sendEmailVerification(){

        val util = FirebaseAuth.getInstance().currentUser!!

        if (!util.isEmailVerified) {
            util.sendEmailVerification()
            MaterialAlertDialogBuilder(this)
                .setIcon(R.drawable.ic_email_check)
                .setTitle("Confirmer votre compte")
                .setMessage("Vérifier votre boîte mail pour confirmer votre compte")
                .setPositiveButton("OK") { _, _ -> }
                .show()
        } else {
            startActivity(Intent(this@ConnexionActivity, RCVBAppActivity::class.java))
            finish()
        }

    }

    private fun googleAuth() {

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val btnGoogle = binding.btnGoogle

        btnGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        val signInClient = GoogleSignIn.getClient(this, options)
        signInClient.signInIntent.also {
            startActivityForResult(it, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
            account?.let {
                firebaseAuthWithGoogle(it)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {

        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                mAuth.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ConnexionActivity, "Connexion réussie", Toast.LENGTH_LONG)
                        .show()
                    val intent = Intent(this@ConnexionActivity, RCVBAppActivity::class.java)
                    startActivity(intent)
                    this@ConnexionActivity.finish()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ConnexionActivity, "Identifiant et/ou mot de passe incorrect", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }



}