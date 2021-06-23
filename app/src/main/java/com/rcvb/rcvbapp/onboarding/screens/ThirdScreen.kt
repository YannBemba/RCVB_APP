package com.rcvb.rcvbapp.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.FragmentThirdBinding

class ThirdScreen : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(layoutInflater)

        binding.nextThree.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_homeAuthFragment)
        }

        return binding.root
    }

}