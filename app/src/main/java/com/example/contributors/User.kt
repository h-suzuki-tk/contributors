package com.example.contributors

import android.os.Parcelable
import android.util.Log
import com.eclipsesource.json.Json
import com.eclipsesource.json.JsonObject
import com.eclipsesource.json.JsonValue
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.*
import java.io.Serializable

class ELEMENTS {
    companion object {
        const val LOGIN = "login"
        const val ID = "id"
        const val NODE_ID = "node_id"
        const val AVATAR_URL = "avatar_url"
        const val GRAVATAR_ID = "gravatar_id"
        const val URL = "url"
        const val HTML_URL = "html_url"
        const val FOLLOWERS_URL = "followers_url"
        const val FOLLOWING_URL = "following_url"
        const val GISTS_URL = "gists_url"
        const val STARRED_URL = "starred_url"
        const val SUBSCRIPTION_URL = "subscriptions_url"
        const val ORGANIZATIONS_URL = "organizations_url"
        const val REPOS_URL = "repos_url"
        const val EVENTS_URL = "events_url"
        const val RECEIVED_EVENTS_URL = "received_events_url"
        const val TYPE = "type"
        const val SITE_ADMIN = "site_admin"
        const val NAME = "name"
        const val COMPANY = "company"
        const val BLOG = "blog"
        const val LOCATION = "location"
        const val HIREABLE = "hireable"
        const val EMAIL = "email"
        const val BIO = "bio"
        const val TWITTER_USERNAME = "twitter_username"
        const val PUBLIC_REPOS = "public_repos"
        const val PUBLIC_GISTS = "public_gists"
        const val FOLLOWERS = "followers"
        const val FOLLOWING = "following"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
        val LIST = listOf(
                LOGIN, ID, NODE_ID, AVATAR_URL, GRAVATAR_ID, URL, HTML_URL,
                FOLLOWERS_URL, FOLLOWING_URL, GISTS_URL, STARRED_URL, SUBSCRIPTION_URL,
                ORGANIZATIONS_URL, REPOS_URL, EVENTS_URL, RECEIVED_EVENTS_URL, TYPE, SITE_ADMIN,
                NAME, COMPANY, BLOG, LOCATION, HIREABLE, EMAIL, BIO, TWITTER_USERNAME,
                PUBLIC_REPOS, PUBLIC_GISTS, FOLLOWERS, FOLLOWING, CREATED_AT, UPDATED_AT
        )
    }
}

@Parcelize
class User(private val obj : JsonObject) : Parcelable {

    companion object {
        suspend fun read(client : HTTP, url: String) : User? = withContext(Dispatchers.Default) {
            Json.parse(client.get(url)).let {
                if ( it.isFalse || it.isNull || !it.isObject ) { null }
                else { User(it.asObject()) }
            }
        }
    }

    private val _elements = mutableMapOf<String, String>()

    init {
        for (el in ELEMENTS.LIST) {
            obj.get(el).let {
                _elements += when (it) {
                    null -> mapOf(el to "null")
                    else -> mapOf(el to it.toString()
                            .replace("\"", "")
                            .replace("\\{.*\\}".toRegex(), ""))
                }
            }
        }
    }

    fun element(key : String) : String? {
        return when (_elements[key]) {
            ""     -> null
            "null" -> null
            else   -> _elements[key]
        }
    }

}
