package ru.netology.nmedia.services

import com.google.gson.annotations.SerializedName

class NewPostContent(
    @SerializedName("userId")
    val userId: Long,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("postContent")
    val postContent: String
)
