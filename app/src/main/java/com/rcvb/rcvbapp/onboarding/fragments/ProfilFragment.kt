package com.rcvb.rcvbapp.onboarding.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.core.FirestoreClient
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.installations.remote.TokenResult
import com.google.firebase.ktx.Firebase
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.DialogPlusBuilder
import com.orhanobut.dialogplus.ViewHolder
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.FragmentProfilBinding
import com.rcvb.rcvbapp.entites.FirestoreCollections
import com.rcvb.rcvbapp.entites.Utilisateur
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class ProfilFragment: Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    private val utilCollectionsRef = Firebase.firestore.collection("utilisateurs")
    private val TAG = "ProfilFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(layoutInflater)

        mAuth = FirebaseAuth.getInstance()

        // Afficher les données de l'utilisateur

        val userId = utilCollectionsRef.document().id
        val userRef = utilCollectionsRef.document(userId)
        val userQuery = utilCollectionsRef.whereEqualTo("id", 2)
                .get()

        userRef.addSnapshotListener { querySnapshot, error ->
            userQuery.let {
                binding.nomProfil.text = querySnapshot?.getString("nom")
                binding.prenomProfil.text = querySnapshot?.getString("prenom")
                binding.emailProfil.text = querySnapshot?.getString("email")
                binding.telProfil.text = querySnapshot?.getString("tel")
            }
        }

        val btnNewProfil = binding.btnModifier
        val btnDeconnecter = binding.btnDeconnexion

        btnNewProfil.setOnClickListener {
            Toast.makeText(requireContext(), "UID  : $userId", Toast.LENGTH_LONG).show()
            updateUtil()
        }

        btnDeconnecter.setOnClickListener {
            showDialogLogout()
        }

        return binding.root

    }

    private fun showDialogLogout() {
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
                requireActivity().finish()
            })
            .setNegativeButton("Annuler") { _, _ -> }
            .show()

        //Ajouter l'équivalent du finish()
    }

    private fun updateUtil() {
        val dialogPlus = DialogPlus.newDialog(requireContext())
            .setContentHolder(ViewHolder(R.layout.dialog_profil))
            .setExpanded(true, 1600)
            .create()

        dialogPlus.show()

        val myView = dialogPlus.holderView

        val nomEditProfil: TextInputLayout = myView.findViewById(R.id.tilNomProfil)
        val prenomEditProfil: TextInputLayout = myView.findViewById(R.id.tilPrenomProfil)
        val emailEditProfil: TextInputLayout = myView.findViewById(R.id.tilEmailProfil)
        val telEditProfil: TextInputLayout = myView.findViewById(R.id.tilTelProfil)
        val btnEnregistrer: Button = myView.findViewById(R.id.btnEnregistrer)

        val nom: CharSequence
        val prenom: CharSequence
        val email: CharSequence
        val tel: CharSequence

        nom = nomEditProfil.editText!!.text
        prenom = prenomEditProfil.editText!!.text
        email = emailEditProfil.editText!!.text
        tel = telEditProfil.editText!!.text

        if(nom.isEmpty()){
            nomEditProfil.error = "Le nom est requis"
            nomEditProfil.requestFocus()
            return
        }

        if(prenom.isEmpty()){
            prenomEditProfil.error = "Le prénom est requis"
            prenomEditProfil.requestFocus()
            return
        }

        if(email.isEmpty()){
            emailEditProfil.error = "L'email est requis"
            emailEditProfil.requestFocus()
            return
        }

        // Renforcer la validation de l'email

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditProfil.error = "Veuillez saisir un email valide"
            emailEditProfil.requestFocus()
            return
        }

        if(tel.isEmpty()){
            telEditProfil.error = "Le numéro de téléphone est requis"
            telEditProfil.requestFocus()
            return
        }

        if(tel.length != 10){
            telEditProfil.error = "Veuillez entrer un numéro valide"
            telEditProfil.requestFocus()
            return
        }

        btnEnregistrer.setOnClickListener {
            val map = mutableMapOf<String, Any>(
                "nom" to nom.toString(),
                "prenom" to prenom.toString(),
                "email" to email.toString(),
                "tel" to tel.toString()
            )

            val userRef = utilCollectionsRef.document("MVhD58KqkEbzmOUK0iMy")
            userRef.update(map)
                .addOnSuccessListener { TODO("Not yet implemented") }

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

        requireActivity().finish()

    }
}