package com.example.rcvb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rcvb.databinding.FragmentAProposBinding
import kotlinx.coroutines.flow.callbackFlow

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