package ru.basket.cache

import ru.basket.features.registation.RegisterReceive

data class TokenCache(
    val login : String,
    val token : String
)

object InMemoryCache {
    val userList: MutableList<RegisterReceive> = mutableListOf()
    val token: MutableList<TokenCache> = mutableListOf()
}
