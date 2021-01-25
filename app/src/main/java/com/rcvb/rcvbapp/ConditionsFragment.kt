package com.rcvb.rcvbapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rcvb.rcvbapp.databinding.FragmentConditionsBinding

class ConditionsFragment : Fragment() {

    private var _binding: FragmentConditionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentConditionsBinding.inflate(layoutInflater)

        return binding.root
    }

}