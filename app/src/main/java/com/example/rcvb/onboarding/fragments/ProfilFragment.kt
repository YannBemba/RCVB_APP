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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(layoutInflater)

        val btnDeconnecter = binding.btnDeconnexion

        btnDeconnecter.setOnClickListener {

            // Alert Dialog

            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_profilFragmentNav_to_mainActivity)
        }

        return binding.root
    }

}