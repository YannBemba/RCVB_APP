package com.rcvb.rcvbapp.onboarding.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rcvb.rcvbapp.databinding.FragmentBoutiqueBinding
import com.rcvb.rcvbapp.databinding.FragmentClubBinding
import com.rcvb.rcvbapp.entites.Article

class ArticleFragment : Fragment() {

    private var _binding: FragmentClubBinding? = null
    private val binding get() = _binding!!

    // Création de la db Article
    private val dbArticle = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClubBinding.inflate(layoutInflater)

        val query = dbArticle.collection("article")

        val options = FirestoreRecyclerOptions.Builder<Article>().setQuery(query, Article::class.java)
            .setLifecycleOwner(this)
            .build()

        // Création de l'adapter

        return binding.root
    }

}