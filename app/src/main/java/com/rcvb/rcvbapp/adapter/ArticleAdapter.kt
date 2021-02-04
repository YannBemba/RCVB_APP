package com.rcvb.rcvbapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.ItemArticleClubBinding
import com.rcvb.rcvbapp.entites.Article

class ArticleAdapter(options: FirestoreRecyclerOptions<Article>)
    : FirestoreRecyclerAdapter<Article, ArticleAdapter.ArticleViewHolder>(options) {

    class ArticleViewHolder(val binding: ItemArticleClubBinding): RecyclerView.ViewHolder(binding.root) {

        var categorie = binding.categorieArticle
        var titre = binding.titreArticle
        //var description = binding.descriptionArticle
        var imageArt = binding.imageArticle
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

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int, model: Article) {

        holder.categorie.text = model.categorie
        holder.titre.text = model.titre
        //holder.binding.descriptionArticle.text = model.description

        Glide.with(holder.itemView)
                .load(Uri.parse(model.url))
                .into(holder.imageArt)

        // Vérifier si l'article a été liké

        if(model.isLike) {
            holder.imgLike.setImageResource(R.drawable.ic_favorite_fill)
        } else {
            holder.imgLike.setImageResource(R.drawable.ic_favorite)
        }

        holder.imgLike.setOnClickListener {
            // Inverser si le bouton est like ou non
            model.isLike = !model.isLike
        }

    }



}