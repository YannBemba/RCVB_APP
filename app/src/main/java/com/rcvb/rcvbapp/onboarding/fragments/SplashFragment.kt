package com.rcvb.rcvbapp.onboarding.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.FragmentSpashBinding


class SplashFragment : Fragment() {

    private var _binding: FragmentSpashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpashBinding.inflate(layoutInflater)

        Handler().postDelayed({
            findNavController().navigate(R.id.action_spashFragment_to_viewPagerFragment)
        }, 3000)

        return binding.root
    }

}