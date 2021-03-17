package com.rcvb.rcvbapp.entites

import java.math.BigInteger
import java.security.MessageDigest

data class Utilisateur(
        var id: Int = 0,
        var nom: String = "",
        var prenom: String = "",
        var email: String = "",
        var tel: String = "",
        var mdp: String = ""
) {
    init {
        id++
        mdp = md5(mdp)
    }
}

private fun md5(input:String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}