package com.rcvb.rcvbapp.entites

data class Article(
        var categorie: String = "",
        var titre: String = "",
        var url: String = "gs://rcvb-41297.appspot.com/images/bird.jpg",
        var image: String = "",
        var description: String = "",
        var isLike: Boolean = false,
        var commentaire: String = ""
)