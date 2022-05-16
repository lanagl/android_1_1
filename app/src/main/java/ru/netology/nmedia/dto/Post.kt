package ru.netology.nmedia

import java.util.*

data class Post(
    val id: Long,
    val text: String,
    val author: String,
    val date: Date,
    val likes: Likes,
    val views: Int,
    val reposts: Int,
    val video: String?
)
data class Likes(
    val count: Int,
    val userLikes: Boolean
)
