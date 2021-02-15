package com.rcvb.rcvbapp.onboarding.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rcvb.rcvbapp.adapter.ArticleAdapter
import com.rcvb.rcvbapp.adapter.RechercheAdapter
import com.rcvb.rcvbapp.databinding.FragmentArticleBinding
import com.rcvb.rcvbapp.entites.Article
import com.rcvb.rcvbapp.entites.FirestoreCollections

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val articleCollectionRef = Firebase.firestore.collection("articles")

    private var articleAdapter: ArticleAdapter? = null
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(layoutInflater)

        val edRecherche = binding.edRecherche
        val btnRechercher = binding.btnRechercher

        btnRechercher.setOnClickListener {
            if(edRecherche.text.toString().isNotBlank() || edRecherche.text.toString().isNotEmpty()
                    || edRecherche.text.toString().isDigitsOnly()){
                firestoreRecherche()
            }
        }
        setUpRecyclerView()

        return binding.root
    }

    private fun firestoreRecherche() {

        val firstChar = binding.edRecherche.text.first().toString().toLowerCase()

        val articleQuery: Query = articleCollectionRef.orderBy("titre").startAt(firstChar).endAt(firstChar + "\uf8ff")
        val articleRecyclerView = binding.articleRecyclerview

        val firestoreRecyclerOptions: FirestoreRecyclerOptions<Article> = FirestoreRecyclerOptions.Builder<Article>()
                .setQuery(articleQuery, Article::class.java)
                .build()

        articleAdapter = ArticleAdapter(firestoreRecyclerOptions)

        articleRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        articleAdapter!!.startListening()
        articleRecyclerView.adapter = articleAdapter
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