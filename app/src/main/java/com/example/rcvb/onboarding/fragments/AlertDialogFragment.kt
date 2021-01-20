package com.example.rcvb.onboarding.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.rcvb.R
import com.example.rcvb.databinding.FragmentAlertDialogBinding

class AlertDialogFragment : DialogFragment() {

    private var _binding: FragmentAlertDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentAlertDialogBinding.inflate(layoutInflater)

        return binding.root
    }
}