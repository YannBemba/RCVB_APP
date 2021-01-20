package com.example.rcvb.forms

// Facebook imports

import com.facebook.appevents.AppEventsLogger;


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.rcvb.R
import com.example.rcvb.RCVBAppActivity
import com.example.rcvb.databinding.ActivityConnexionBinding
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import java.util.*

class ConnexionActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 23
    }

    private lateinit var binding: ActivityConnexionBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var callbackManager: CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnexionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Connexion à un compte Google

        mAuth = FirebaseAuth.getInstance()

        this.googleSignIn()
        this.facebookAuth()

        val btnConnexion = binding.btnConnexion
        val tvInscription = binding.tvInscription
        val mdpOublie = binding.tvMdpOublie

        btnConnexion.setOnClickListener {
            userLogin()
        }

        tvInscription.setOnClickListener {
            startActivity(Intent(this, InscriptionActivity::class.java))
        }

        mdpOublie.setOnClickListener {
            startActivity(Intent(this, ForgotActivity::class.java))
        }

    }

    private fun getEmailEdit(): EditText {
        return binding.etEmailConnexion
    }

    private fun getMdpEdit(): EditText {
        return binding.etMdpConnexion
    }

    private fun userLogin() {

        val email = this.getEmailEdit().text.toString().trim()
        val mdp = this.getMdpEdit().text.toString().trim()

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
        mAuth.signInWithEmailAndPassword(email, mdp)
            .addOnCompleteListener { tache ->
                if (tache.isSuccessful) {
                    val utils = FirebaseAuth.getInstance().currentUser
                    if(utils!!.isEmailVerified) {
                        //Redirectionner vers la page d'accueil
                        startActivity(Intent(this@ConnexionActivity, RCVBAppActivity::class.java))
                        finish()
                    } else {
                        utils.sendEmailVerification()
                        Toast.makeText(
                            this@ConnexionActivity,
                            "Vérifier votre email pour confirmer votre compte",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                } else {
                    /*val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)
                    snackbar.show()*/
                    Toast.makeText(
                        this,
                        "Erreur de connexion ! Vérifier vos champs",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
    }

    private fun googleSignIn() {

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
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val TAG = ConnexionActivity::class.simpleName

        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            //Créer une exception
            val exception = task.exception

            if(task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e)
                }
            } else {
                Log.w(TAG, exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val TAG = ConnexionActivity::class.simpleName

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val intent = Intent(this, RCVBAppActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun facebookAuth() {

        // Initialisation du SDK Facebook

        FacebookSdk.sdkInitialize(this)

        val TAG = ConnexionActivity::class.simpleName

        callbackManager = CallbackManager.Factory.create()

        val customFbButton = binding.btnFacebook

        customFbButton.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this,
                listOf("email", "public_profile")
            )
            LoginManager.getInstance().registerCallback(callbackManager, object :
                FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(TAG, "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                }

                override fun onError(error: FacebookException) {
                }

            })

        }
    }

    override fun onStart() {
        super.onStart()

        //Si l'utilisateur est connecter à Facebook, directionner à la HomePage

        val currentUser = mAuth.currentUser

        if(currentUser != null) {
            updateUI(currentUser)
        }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }*/

    private fun handleFacebookAccessToken(token: AccessToken) {

        val TAG = ConnexionActivity::class.simpleName
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null) {
            val intent = Intent(this@ConnexionActivity, RCVBAppActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Inscris toi pour continuer", Toast.LENGTH_SHORT)
                .show()
        }
    }


}