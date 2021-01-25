package com.rcvb.rcvbapp.forms

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.RCVBAppActivity
import com.rcvb.rcvbapp.SuccessActivity
import com.rcvb.rcvbapp.databinding.ActivityConnexionBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConnexionActivity: AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 23
    }

    private lateinit var binding: ActivityConnexionBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnexionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Connexion à un compte Google

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

    private fun getEmailEdit(): EditText {
        return binding.etEmailConnexion
    }

    private fun getMdpEdit(): EditText {
        return binding.etMdpConnexion
    }

    private suspend fun applyAnimations() {

        binding.tvTitreConnexion.animate()
            .alpha(0f)
            .duration = 400L

        binding.view3.animate()
            .alpha(0f)
            .duration = 400L

        binding.linearConnect.animate()
            .alpha(0f)
            .translationXBy(1200f)
            .duration = 400L

        binding.btnConnexion.animate()
            .alpha(0f)
            .duration = 400L

        binding.tvMdpOublie.animate()
            .alpha(0f)
            .translationXBy(-1200f)
            .duration = 400L

        binding.tvInscription.animate()
            .alpha(0f)
            .translationXBy(1200f)
            .duration = 400L

        binding.linearApi.animate()
            .alpha(0f)
            .duration = 400L

        delay(300)

        binding.successBg.animate().alpha(1f).duration = 600L
        binding.successBg.animate().rotationBy(90f).duration = 600L
        binding.successBg.animate().scaleXBy(900f).duration = 800L
        binding.successBg.animate().scaleYBy(900f).duration = 600L

        delay(500)

        binding.successLottie.animate().alpha(1f).duration = 400L

        delay(2000L)

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
                            val intent = Intent(this@ConnexionActivity, RCVBAppActivity::class.java)
                            startActivity(intent)
                            /*lifecycleScope.launch {
                                applyAnimations()
                            }*/
                            finish()
                        } else {
                            utils.sendEmailVerification()
                            MaterialAlertDialogBuilder(this)
                                    .setIcon(R.drawable.ic_email_check)
                                    .setTitle("Confirmer votre compte")
                                    .setMessage("Vérfier votre boîte mail pour confirmer votre compte RCVB")
                                    .setBackground(resources.getDrawable(R.drawable.bg_message, null))
                                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

                                    })
                                    .show()
                        }
                    } else {
                        Toast.makeText(
                                this,
                                "Erreur de connexion ! Vérifier vos champs",
                                Toast.LENGTH_LONG
                        ).show()
                    }
                }
    }

    private fun getTagActivity(): String? {
        return ConnexionActivity::class.simpleName
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
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val TAG = this.getTagActivity()

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if(task.isSuccessful) {
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

        val TAG = this.getTagActivity()

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    //val user = mAuth.currentUser

                    val intent = Intent(this, RCVBAppActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
}