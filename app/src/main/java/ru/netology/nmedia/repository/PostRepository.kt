package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(callback: GetAllCallback)
    fun getById(id: Long, callback: PostCallback)
    fun likeById(id: Long, isLiked: Boolean, callback: PostCallback)
    fun save(post: Post, callback: SaveRemoveCallback)
    fun removeById(id: Long, callback: SaveRemoveCallback)

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>) {}
        fun onError(e: Exception) {}
    }

    interface PostCallback {
        fun onSuccess(post: Post) {}
        fun onError(e: Exception) {}
    }

    interface SaveRemoveCallback {
        fun onSuccess() {}
        fun onError(e: Exception) {}
    }

}
