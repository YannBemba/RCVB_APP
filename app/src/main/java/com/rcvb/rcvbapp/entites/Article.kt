package com.rcvb.rcvbapp.entites



data class Article(
    var id: Int = 0,
    var categorie: String = "",
    var titre: String = "",
    var url: String = "",
    var description: String = "",
    var datePublication: String,
    var isLike: Boolean = false,
    var commentaire: String = ""
){
    constructor() : this(0,"", "", "", "", "", false, "")
    constructor(titre: String) : this()
}