package com.rcvb.rcvbapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rcvb.rcvbapp.databinding.FragmentArticleBinding
import com.rcvb.rcvbapp.entites.Article

class RechercheAdapter(options: FirestoreRecyclerOptions<Article>): FirestoreRecyclerAdapter<Article, RechercheAdapter.RechercheViewHolder>(options) {
    class RechercheViewHolder(val binding: FragmentArticleBinding): RecyclerView.ViewHolder(binding.root) {
        var searchField = binding.edRecherche
        var searchBtn = binding.btnRechercher
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RechercheViewHolder {
        return RechercheViewHolder(FragmentArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: RechercheViewHolder, position: Int, model: Article) {

    }

    private fun firestoreRecherche() {

    }

}