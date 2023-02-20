package ru.netology.nmedia.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.Post

interface PostRepository {

    val data: Flow<List<Post>>
    suspend fun getAll()

    suspend fun getById(id: Long)
    suspend fun likeById(id: Long, isLiked: Boolean)
    suspend fun save(post: Post)
    suspend fun removeById(id: Long)
    fun getNewer(id: Long): Flow<Int>

    suspend fun showAll()

}
