package ru.netology.nmedia

import kotlinx.serialization.Serializable
import kotlinx.datetime.serializers.LocalDateComponentSerializer
import java.time.LocalDate

@Serializable
data class Post(
    val id: Long,
    val text: String,
    val author: String,
    val date: Long,
    val views: Int,
    val reposts: Int,
    val likesCount: Int,
    val likeByMe: Boolean,
    val video: String?
)
