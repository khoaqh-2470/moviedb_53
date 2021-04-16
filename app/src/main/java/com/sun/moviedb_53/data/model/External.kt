package com.sun.moviedb_53.data.model

data class External(
    val id: Int,
    val facebook: String?,
    val twitter: String?,
    val instagram: String?
)

object ExternalEntry {
    const val ID = "id"
    const val FACEBOOK = "facebook_id"
    const val TWITTER = "twitter_id"
    const val INSTAGRAM = "instagram_id"
}
