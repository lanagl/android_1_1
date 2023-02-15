package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {

    fun data(): LiveData<List<Post>>
    suspend fun getAll()
    suspend fun getById(id: Long)
    suspend fun likeById(id: Long, isLiked: Boolean)
    suspend fun save(post: Post)
    suspend fun removeById(id: Long)

}
