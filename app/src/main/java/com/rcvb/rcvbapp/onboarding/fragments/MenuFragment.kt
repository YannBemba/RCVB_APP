package com.rcvb.rcvbapp.onboarding.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentMenuBinding.inflate(layoutInflater)

        val btnClub = binding.clubCard
        val btnBoutique = binding.boutiqueCard

        btnClub.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragmentNav_to_clubFragment)
        }

        btnBoutique.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragmentNav_to_boutiqueFragment)
        }

        return binding.root
    }


}