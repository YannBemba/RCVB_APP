package com.rcvb.rcvbapp.onboarding.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.rcvb.rcvbapp.adapter.ArticleAdapter
import com.rcvb.rcvbapp.databinding.FragmentClubBinding
import com.rcvb.rcvbapp.entites.Article
import com.rcvb.rcvbapp.entites.FirestoreCollections

class ArticleFragment : Fragment() {

    private var _binding: FragmentClubBinding? = null
    private val binding get() = _binding!!

    private val articleCollectionRef = FirestoreCollections.FIRESTORE_ARTICLES

    private var articleAdapter: ArticleAdapter? = null
    
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClubBinding.inflate(layoutInflater)

        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {

        val articleQuery: Query = articleCollectionRef
        val articleRecyclerView = binding.articleRecyclerview

        val firestoreRecyclerOptions: FirestoreRecyclerOptions<Article> = FirestoreRecyclerOptions.Builder<Article>()
                .setQuery(articleQuery, Article::class.java)
                .build()


        articleAdapter = ArticleAdapter(firestoreRecyclerOptions)

        articleRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        articleRecyclerView.adapter = articleAdapter

    }

    override fun onStart() {
        super.onStart()
        articleAdapter?.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        articleAdapter?.stopListening()
    }


}