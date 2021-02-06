package com.rcvb.rcvbapp.onboarding.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.FragmentArticleDescBinding


class ArticleDescFragment() : Fragment() {

    private var _binding: FragmentArticleDescBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDescBinding.inflate(layoutInflater)

        return binding.root
    }

}