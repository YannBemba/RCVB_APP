package com.rcvb.rcvbapp.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.FragmentFirstBinding

class FirstScreen : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(layoutInflater)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewpager)

        binding.nextOne.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}