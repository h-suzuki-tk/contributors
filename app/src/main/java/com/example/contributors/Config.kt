package com.example.contributors

const val CONTRIBUTORS_URL = "https://api.github.com/repositories/90792131/contributors"

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
        const val CONTRIBUTIONS = "contributions"
        val LIST = listOf(LOGIN, ID, NODE_ID, AVATAR_URL, GRAVATAR_ID, URL, HTML_URL,
            FOLLOWERS_URL, FOLLOWING_URL, GISTS_URL, STARRED_URL, SUBSCRIPTION_URL,
            ORGANIZATIONS_URL, REPOS_URL, EVENTS_URL, RECEIVED_EVENTS_URL, TYPE, SITE_ADMIN,
            CONTRIBUTIONS)
    }
}
