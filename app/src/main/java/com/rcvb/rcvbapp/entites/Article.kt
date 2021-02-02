package com.rcvb.rcvbapp.entites

import android.graphics.Bitmap

data class Article(
    var titre: String,
    var image: Bitmap,
    var description: String,
    var nbLike: Int,
    var commentaire: String
)