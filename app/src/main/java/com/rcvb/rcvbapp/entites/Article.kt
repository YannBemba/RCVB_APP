package com.rcvb.rcvbapp.entites

data class Article(
    var id: String = "",
    var categorie: String = "",
    var titre: String = "",
    var url: String = "",
    var description: String = "",
    var datePublication: String,
    var liked: Boolean = false,
    var commentaire: String = ""
){
    constructor() : this("","", "", "", "", "", false, "")

    constructor(titre: String) : this()
}