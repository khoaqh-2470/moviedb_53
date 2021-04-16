package com.sun.moviedb_53.data.source.remote.fetchjson

import com.sun.moviedb_53.data.model.*
import com.sun.moviedb_53.utils.KeyEntityType
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ParseDataWithJson {

    fun getJsonFromUrl(urlString: String?): String? {
        try {
            val url = URL(urlString)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.apply {
                connectTimeout = TIME_OUT
                readTimeout = TIME_OUT
                requestMethod = METHOD_GET
                doOutput = true
                connect()
            }
            val bufferedReader = BufferedReader(InputStreamReader(url.openStream()))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            bufferedReader.close()
            httpURLConnection.disconnect()
            return stringBuilder.toString()
        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }
    }

    fun parseJsonToData(jsonObject: JSONObject?, keyEntityType: KeyEntityType): Any? {
        return try {
            when (keyEntityType) {
                KeyEntityType.MOVIE_ITEM -> {
                    parseJsonToList(
                        jsonObject?.getJSONArray(HotMovieEntry.MOVIE),
                        keyEntityType
                    )
                }
                KeyEntityType.MOVIE_DETAIL -> {
                    parseJsonToObject(
                        jsonObject,
                        keyEntityType
                    )
                }
                KeyEntityType.GENRES_ITEM -> {
                    parseJsonToList(
                        jsonObject?.getJSONArray(GenresEntry.LIST_GENRES),
                        keyEntityType
                    )
                }
                KeyEntityType.ACTOR -> {
                    parseJsonToList(
                        jsonObject?.getJSONArray(ActorEntry.LIST_ACTOR),
                        keyEntityType
                    )
                }
                KeyEntityType.VIDEO_YOUTUBE -> {
                    parseJsonToList(
                        jsonObject?.getJSONArray(VideoYoutubeEntry.LIST_VIDEOS),
                        keyEntityType
                    )
                }
                KeyEntityType.GENRES_MOVIE_ITEM -> {
                    parseJsonToList(
                        jsonObject?.getJSONArray(GenresMovieEntry.MOVIE),
                        keyEntityType
                    )
                }
                KeyEntityType.ACTOR_DETAIL -> {
                    parseJsonToObject(
                        jsonObject,
                        keyEntityType
                    )
                }
                KeyEntityType.EXTERNAL_ACTOR -> {
                    parseJsonToObject(
                        jsonObject,
                        keyEntityType
                    )
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun parseJsonToList(jsonArray: JSONArray?, typeModel: KeyEntityType): Any? {
        return try {
            val data = mutableListOf<Any?>()
            for (i in 0 until (jsonArray?.length() ?: 0)) {
                val jsonObject = jsonArray?.getJSONObject(i)
                data.add(parseJsonToObject(jsonObject, typeModel))
            }
            data.filterNotNull()
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }
    }

    private fun parseJsonToObject(jsonObject: JSONObject?, keyEntityType: KeyEntityType): Any? {
        try {
            jsonObject?.let {
                return when (keyEntityType) {
                    KeyEntityType.MOVIE_ITEM -> {
                        ParseJson().movieParseJson(it)
                    }
                    KeyEntityType.MOVIE_DETAIL -> {
                        ParseJson().movieDetailParseJson(it)
                    }
                    KeyEntityType.GENRES_ITEM -> {
                        ParseJson().genresParseJson(it)
                    }
                    KeyEntityType.ACTOR -> {
                        ParseJson().actorParseJson(it)
                    }
                    KeyEntityType.VIDEO_YOUTUBE -> {
                        ParseJson().videoYoutubeParseJson(it)
                    }
                    KeyEntityType.GENRES_MOVIE_ITEM -> {
                        ParseJson().genresMovieParseJson(it)
                    }
                    KeyEntityType.ACTOR_DETAIL -> {
                        ParseJson().actorDetailParseJson(it)
                    }
                    KeyEntityType.EXTERNAL_ACTOR -> {
                        ParseJson().externalParseJson(it)
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    companion object {
        private const val TIME_OUT = 15000
        private const val METHOD_GET = "GET"
    }
}
