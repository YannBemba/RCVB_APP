package com.rcvb.rcvbapp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rcvb.rcvbapp.databinding.ItemArticleClubBinding
import com.rcvb.rcvbapp.entites.Article
import com.rcvb.rcvbapp.onboarding.fragments.ArticleFragmentDirections


class ArticleAdapter(options: FirestoreRecyclerOptions<Article>)
    : FirestoreRecyclerAdapter<Article, ArticleAdapter.ArticleViewHolder>(options) {

    private val TAG = "ArticleAdapter"

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
        var description: String = ""
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
        holder.description = model.description
        "Publié le ${model.datePublication}".also { holder.datePub.text = it }

        holder.imgCommentaire.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Pas encore disponible", Toast.LENGTH_LONG)
                    .show()
        }

        Log.d(TAG, "Url image = ${model.url} ")

        // Conversion du TimeStamp en String

        Glide.with(holder.itemView.context)
                .load(model.url)
                .into(holder.imageArt)

        holder.item_article.setOnClickListener {

            val action = ArticleFragmentDirections.actionClubFragmentToArticleDescFragment(
                    model.url,
                    holder.datePub.text.toString(),
                    holder.categorie.text.toString(),
                    holder.titre.text.toString(),
                    holder.description
            )
            it.findNavController().navigate(action)

        }

        // Système de partage

        holder.imgPartager.setOnClickListener {
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, model.datePublication)
                this.putExtra(Intent.EXTRA_TEXT, model.categorie)
                this.putExtra(Intent.EXTRA_TEXT, model.titre)
                this.putExtra(Intent.EXTRA_TEXT, model.description)
                this.type = "text/plain"
            }
            holder.itemView.context.startActivity(shareIntent)
        }

    }


}