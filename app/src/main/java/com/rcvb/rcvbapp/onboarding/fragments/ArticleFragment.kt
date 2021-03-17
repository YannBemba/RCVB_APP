package com.rcvb.rcvbapp.onboarding.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rcvb.rcvbapp.adapter.ArticleAdapter
import com.rcvb.rcvbapp.adapter.ArticleItemDecoration
import com.rcvb.rcvbapp.databinding.FragmentArticleBinding
import com.rcvb.rcvbapp.entites.Article
import java.util.*

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val articleCollectionRef = Firebase.firestore.collection("articles")

    private var articleAdapter: ArticleAdapter? = null

    private var titreNotif = ""
    private var messageNotif = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(layoutInflater)

        val edRecherche = binding.edRecherche
        val btnRechercher = binding.btnRechercher

        btnRechercher.setOnClickListener {
            if(edRecherche.text.toString().isNotBlank()
                    || edRecherche.text.toString().isNotEmpty()){
                firestoreRecherche()
            }
        }
        setUpRecyclerView()

        return binding.root
    }

    private fun firestoreRecherche() {

        val recherche = binding.edRecherche.text.toString().toUpperCase().first().toString() //toString().toUpperCase(Locale.ROOT)

        val articleQuery: Query = articleCollectionRef.orderBy("titre").startAt(recherche).endAt(recherche + "\uf8ff")
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

        if(requireActivity().intent.extras != null) {
            for(key in requireActivity().intent.extras!!.keySet()){
                if(key == "titre"){
                    titreNotif = requireActivity().intent.extras!!.getString("titre", "")
                }
                if(key == "message"){
                    messageNotif = requireActivity().intent.extras!!.getString("message", "")
                }
            }
        }

        articleAdapter = ArticleAdapter(firestoreRecyclerOptions)

        articleRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        articleRecyclerView.adapter = articleAdapter

        articleRecyclerView.addItemDecoration(ArticleItemDecoration())

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