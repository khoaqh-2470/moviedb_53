package com.sun.moviedb_53.data.source.local.database.table

object FavoriteTable {
    const val TABLE_NAME = "table_favorite"
    const val COL_ID = "id"
    const val COL_TITLE = "title"
    const val COL_IMAGE_POSTER = "poster_path"
    const val COL_TAG_LINE = "tagline"
    const val COL_RATE = "vote_average"
    const val DB_MOVIE = ("CREATE TABLE $TABLE_NAME(" +
            "$COL_ID INTEGER PRIMARY KEY, " +
            "$COL_TITLE TEXT, " +
            "$COL_IMAGE_POSTER TEXT, " +
            "$COL_TAG_LINE TEXT, " +
            "$COL_RATE REAL)")
}
