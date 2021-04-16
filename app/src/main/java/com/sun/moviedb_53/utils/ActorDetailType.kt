package com.sun.moviedb_53.utils

enum class ActorDetailType(val path: String) {
    ACTOR("?"),
    EXTERNAL("/external_ids?")
}
