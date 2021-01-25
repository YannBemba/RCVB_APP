package com.rcvb.rcvbapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rcvb.rcvbapp.databinding.FragmentPolitiqueBinding

class PolitiqueFragment : Fragment() {

    private var _binding: FragmentPolitiqueBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPolitiqueBinding.inflate(layoutInflater)

        return binding.root
    }

}