package com.example.rcvb.onboarding.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.rcvb.R
import com.example.rcvb.databinding.FragmentProfilBinding
import com.example.rcvb.entites.Utilisateur
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(layoutInflater)

        mAuth = FirebaseAuth.getInstance()

        val btnDeconnecter = binding.btnDeconnexion

        this.googleProfile()

        btnDeconnecter.setOnClickListener {

            // Alert Dialog

            //FirebaseAuth.getInstance().signOut()
            this.googleSignOut()
            findNavController().navigate(R.id.action_profilFragmentNav_to_mainActivity)
        }

        return binding.root
    }

    private fun googleProfile(){
        val currentUser = mAuth.currentUser
    }

    private fun googleSignOut(){
        mAuth.signOut()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken(context?.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        googleSignInClient.signOut()

    }

}