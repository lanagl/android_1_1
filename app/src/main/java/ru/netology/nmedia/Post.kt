package ru.netology.nmedia

import java.util.*

data class Post(
    val id: Long,
    var text: String,
    val author: String,
    val date: Date,
    var likes: Likes,
    var views: Int,
    var reposts: Int
)
data class Likes(
    var count: Int,
    var userLikes: Boolean
)
