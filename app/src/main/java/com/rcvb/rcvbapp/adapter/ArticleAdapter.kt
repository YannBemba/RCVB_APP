package com.rcvb.rcvbapp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.utils.Logger.error
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.ItemArticleClubBinding
import com.rcvb.rcvbapp.entites.Article
import com.rcvb.rcvbapp.entites.FirestoreCollections

class ArticleAdapter(options: FirestoreRecyclerOptions<Article>)
    : FirestoreRecyclerAdapter<Article, ArticleAdapter.ArticleViewHolder>(options) {

    class ArticleViewHolder(val binding: ItemArticleClubBinding):
            RecyclerView.ViewHolder(binding.root) {
        var item_article = binding.itemArticle // Layout clickable
        var categorie = binding.categorieArticle
        var titre = binding.titreArticle
        var imageArt = binding.imageArticle
        var datePub = binding.datePublication
        var imgLike = binding.imgLike
        var imgPartager = binding.imgPartager
        var imgCommentaire = binding.imgCommentaire
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(ItemArticleClubBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int, model: Article) {

        holder.categorie.text = model.categorie
        holder.titre.text = model.titre
        holder.datePub.text = "Publié le ${model.datePublication}"

        Glide.with(holder.imageArt.context)
                .load(model.url)
                .into(holder.imageArt)

        holder.item_article.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.action_clubFragment_to_articleDescFragment)
        }

        holder.imgPartager.setOnClickListener {
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, "Données partagées entre 2 applications")
                this.type = "text/plain"
            }
            holder.itemView.context.startActivity(shareIntent)
        }

    }



}