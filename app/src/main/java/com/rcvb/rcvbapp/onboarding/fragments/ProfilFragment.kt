package com.rcvb.rcvbapp.onboarding.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.FragmentProfilBinding
import com.rcvb.rcvbapp.entites.FirestoreCollections
import com.rcvb.rcvbapp.entites.Utilisateur
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.StringBuilder

class ProfilFragment: Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    private val utilCollectionsRef = FirestoreCollections.FIRESTORE_UTILS

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(layoutInflater)

        mAuth = FirebaseAuth.getInstance()

        val btnNewProfil = binding.btnEnregistrerProfil

        btnNewProfil.setOnClickListener {
            //val oldUtil =
            val newUtil = getNewUtilMap()
            // Faire en sorte de récupérer les données de l'user concerné
            //updateUtil()
        }

        val btnDeconnecter = binding.btnDeconnexion

        btnDeconnecter.setOnClickListener {

            // Material Alert Dialog
            MaterialAlertDialogBuilder(requireContext())
                    .setIcon(R.drawable.ic_logout)
                    .setTitle("Déconnexion")
                    .setMessage("Etes-vous sûr de vouloir vous déconnecter ?")
                    .setBackground(resources.getDrawable(R.drawable.bg_message, null))
                    .setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
                        // Déconnexion
                        FirebaseAuth.getInstance().signOut()
                        this.googleSignOut()
                        findNavController().navigate(R.id.action_profilFragmentNav_to_mainActivity)
                    })
                    .setNegativeButton("Annuler", DialogInterface.OnClickListener { _, _ ->

                    })
                    .show()

            //Ajouter l'équivalent du finish()
        }

        return binding.root
    }

    private fun getNomProfil(): String {
        return binding.tilNomProfil.editText?.text.toString()
    }
    private fun getPreomProfil(): String {
        return binding.tilPrenomProfil.editText?.text.toString()
    }
    private fun getEmailProfil(): String {
        return binding.tilEmailProfil.editText?.text.toString()
    }
    private fun getTelProfil(): String {
        return binding.tilTelProfil.editText?.text.toString()
    }
    private fun getMdpProfil(): String {
        return binding.tilMdpProfil.editText?.text.toString()
    }

    private fun getNewUtilMap(): Map<String, Any> {
        val nomProfil = this.getNomProfil()
        val prenomProfil = this.getPreomProfil()
        val emailProfil = this.getEmailProfil()
        val telProfil = this.getTelProfil()
        val mdpProfil = this.getMdpProfil()

        val map = mutableMapOf<String, Any>()

        if (nomProfil.isNotEmpty()) {
            map["nom"] = nomProfil
        } else {
            binding.tilNomProfil.error = "Le nom est requis"
            binding.tilNomProfil.requestFocus()
        }

        if (prenomProfil.isNotEmpty()) {
            map["prenom"] = prenomProfil
        } else {
            binding.tilPrenomProfil.error = "Le prénom est requis"
            binding.tilPrenomProfil.requestFocus()
        }

        if (emailProfil.isNotEmpty()) {
            map["email"] = emailProfil
        } else {
            binding.tilEmailProfil.error = "L'email est requis"
            binding.tilEmailProfil.requestFocus()
        }

        // Renforcer la validation de l'email

        if(!Patterns.EMAIL_ADDRESS.matcher(emailProfil).matches()) {
            binding.tilEmailProfil.error = "Veuillez saisir un email valide"
            binding.tilEmailProfil.requestFocus()
        }

        if (telProfil.isNotEmpty()) {
            map["tel"] = telProfil
        } else {
            binding.tilTelProfil.error = "Le numéro de téléphone est requis"
            binding.tilTelProfil.requestFocus()
        }

        if(mdpProfil.isEmpty()){
            binding.tilMdpProfil.error = "Le mdp est requis"
            binding.tilMdpProfil.requestFocus()
        } else {
            map["mdp"] = mdpProfil
        }

        if(mdpProfil.length < 6) {
            binding.tilMdpProfil.error = "Le mot de passe doit contenir plus de 6 caractères"
            binding.tilMdpProfil.requestFocus()
        }

        return map
    }

    private fun updateUtil(util: Utilisateur, newUtilMap: Map<String, Any>) =
            CoroutineScope(Dispatchers.IO).launch {
                val utilQuery = FirestoreCollections.FIRESTORE_UTILS
                        .whereEqualTo("nom", util.nom)
                        .whereEqualTo("prenom", util.prenom)
                        .whereEqualTo("email", util.email)
                        .whereEqualTo("tel", util.tel)
                        .whereEqualTo("mdp", util.mdp)
                        .get()
                        .await()

                if(utilQuery.documents.isNotEmpty()) {
                    for(document in utilQuery) {
                        try {
                            utilCollectionsRef.document(document.id).set(
                                    newUtilMap,
                                    SetOptions.merge()
                            ).await()
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG)
                                        .show()
                            }
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Personne ne correspond à cette personnne dans la db", Toast.LENGTH_LONG)
                                .show()
                    }
                }
            }



    private fun googleSignOut(){
        mAuth.signOut()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        googleSignInClient.signOut()

    }
}