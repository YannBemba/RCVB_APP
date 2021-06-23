package com.rcvb.rcvbapp.onboarding.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.FragmentViewPagerBinding
import com.rcvb.rcvbapp.onboarding.screens.FirstScreen
import com.rcvb.rcvbapp.onboarding.screens.SecondScreen
import com.rcvb.rcvbapp.onboarding.screens.ThirdScreen

class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(layoutInflater)

        binding.vpInscription.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_inscriptionActivity2)
        }

        binding.vpConnexion.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_connexionActivity2)
        }

        val fragmentList = arrayListOf<Fragment>(
                FirstScreen(),
                SecondScreen(),
                ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
                fragmentList,
                requireActivity().supportFragmentManager,
                lifecycle
        )

        binding.viewpager.adapter = adapter

        return binding.root
    }

}