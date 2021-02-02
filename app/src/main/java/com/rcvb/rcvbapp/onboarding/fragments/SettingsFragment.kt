package com.rcvb.rcvbapp.onboarding.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentSettingsBinding.inflate(layoutInflater)

        val conditionBtn = binding.conditionsFragmentCard
        val confidentialiteBtn = binding.confidentialiteFragmentCard
        val aproposBtn = binding.aproposFragmentCard

        conditionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_conditionsFragment)
        }

        confidentialiteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_politiqueFragment)
        }

        aproposBtn.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_AProposFragment)
        }

        return binding.root
    }

}