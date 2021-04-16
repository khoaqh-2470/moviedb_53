package com.sun.moviedb_53.data.model

data class ActorDetail(
    val id: Int?,
    val name: String?,
    val imageUrl: String?,
    val birthday: String?,
    val gender: Int?,
    val address: String?,
)

object ActorDetailEntry {
    const val ID = "id"
    const val NAME = "name"
    const val IMAGE_URL = "name"
    const val BIRTHDAY = "birthday"
    const val GENDER = "gender"
    const val ADDRESS = "place_of_birth"
}
