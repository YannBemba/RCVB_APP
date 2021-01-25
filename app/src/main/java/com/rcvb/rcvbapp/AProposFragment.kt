package com.rcvb.rcvbapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rcvb.rcvbapp.databinding.FragmentAProposBinding

class AProposFragment : Fragment() {

    private var _binding: FragmentAProposBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAProposBinding.inflate(layoutInflater)

        return binding.root
    }


}