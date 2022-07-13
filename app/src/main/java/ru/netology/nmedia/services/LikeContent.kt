package ru.netology.nmedia.services

import com.google.gson.annotations.SerializedName

class LikeContent(
    @SerializedName("userId")
    val userId: Long,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("postId")
    val postId: Long,
    @SerializedName("postAuthor")
    val postAuthor: String
)
