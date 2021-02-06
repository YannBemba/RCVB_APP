package com.rcvb.rcvbapp.entites

import android.widget.ImageView
import com.google.firebase.Timestamp

data class Article(
    var categorie: String = "",
    var titre: String = "",
    var url: String = "",
    var description: String = "",
    var datePublication: Timestamp? = null,
    var isLike: Boolean = false,
    var commentaire: String = ""
)