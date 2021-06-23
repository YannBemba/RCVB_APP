package com.rcvb.rcvbapp.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.FragmentHomeAuthBinding


class HomeAuthFragment : Fragment() {

    private var _binding: FragmentHomeAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAuthBinding.inflate(inflater, container, false)

        binding.homeAuthIns.setOnClickListener {
            findNavController().navigate(R.id.action_homeAuthFragment_to_inscriptionActivity2)
        }

        binding.homeAuthCo.setOnClickListener {
            findNavController().navigate(R.id.action_homeAuthFragment_to_connexionActivity2)
        }

        return binding.root
    }


}