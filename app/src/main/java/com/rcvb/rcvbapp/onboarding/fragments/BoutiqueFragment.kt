package com.rcvb.rcvbapp.onboarding.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rcvb.rcvbapp.databinding.FragmentBoutiqueBinding

class BoutiqueFragment : Fragment() {

    private var _binding: FragmentBoutiqueBinding? = null
    private val binding get() = _binding!!

    private lateinit var webView: WebView
    private val webViewClient: WebViewClient = object: WebViewClient() {}

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoutiqueBinding.inflate(layoutInflater)

        webView = binding.webview
        webView.webViewClient = webViewClient

        webView.loadUrl("https://misterugby.com/gb/")

        // Si l'application utilise du Javascript

        val settings = webView.settings
        settings.javaScriptEnabled = true

        return binding.root
    }

}