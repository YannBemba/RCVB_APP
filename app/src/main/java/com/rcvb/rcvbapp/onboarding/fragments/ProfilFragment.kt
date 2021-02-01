package com.rcvb.rcvbapp.onboarding.fragments

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.FragmentProfilBinding

class ProfilFragment: Fragment() {

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

    private fun getNomProfil(): TextInputLayout {
        return binding.tilNomProfil
    }
    private fun getPreomProfil(): TextInputLayout {
        return binding.tilPrenomProfil
    }
    private fun getEmailProfil(): TextInputLayout {
        return binding.tilEmailProfil
    }
    private fun getTelProfil(): TextInputLayout {
        return binding.tilTelProfil
    }
    private fun getMdpProfil(): TextInputLayout {
        return binding.tilMdpProfil
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