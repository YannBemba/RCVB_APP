package com.rcvb.rcvbapp.entites

import android.widget.ImageView


data class Article(
        var categorie: String = "",
        var titre: String = "",
        var image: String = "",
        var description: String = "",
        var nbLike: Int = 0,
        var commentaire: String = ""
)