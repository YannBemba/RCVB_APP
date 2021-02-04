package com.rcvb.rcvbapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rcvb.rcvbapp.R
import com.rcvb.rcvbapp.databinding.ItemArticleClubBinding
import com.rcvb.rcvbapp.entites.Article
import com.rcvb.rcvbapp.entites.FirestoreCollections

class ArticleAdapter(options: FirestoreRecyclerOptions<Article>)
    : FirestoreRecyclerAdapter<Article, ArticleAdapter.ArticleViewHolder>(options) {

    class ArticleViewHolder(val binding: ItemArticleClubBinding): RecyclerView.ViewHolder(binding.root) {

        var categorie = binding.categorieArticle
        var titre = binding.titreArticle
        //var description = binding.descriptionArticle
        var image = binding.imageArticle
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

        holder.binding.categorieArticle.text = model.categorie
        holder.binding.titreArticle.text = model.titre
        //holder.binding.descriptionArticle.text = model.description

        Glide.with(holder.itemView.context)
                .load(model.image)
                .into(holder.binding.imageArticle)

    }

}