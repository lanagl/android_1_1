package ru.netology.nmedia.repository

import androidx.lifecycle.map
import okhttp3.MediaType.Companion.toMediaType
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity


class PostRepositoryImpl(private val postDao: PostDao) : PostRepository {

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9999"
        private val jsonType = "application/json".toMediaType()
    }

    override suspend fun getById(id: Long) {
        val response = PostsApi.retrofitService.getById(id)
        if (!response.isSuccessful) throw RuntimeException("api error")
        response.body() ?: throw RuntimeException(response.message())
        postDao.getById(id)

    }


    override suspend fun likeById(id: Long, isLiked: Boolean) {
        if (isLiked) {
            val response = PostsApi.retrofitService.dislikeById(id)
            if (!response.isSuccessful) throw RuntimeException("api error")
            response.body() ?: throw RuntimeException(response.message())
            postDao.likeById(id)
        } else {
            val response = PostsApi.retrofitService.likeById(id)
            if (!response.isSuccessful) throw RuntimeException("api error")
            response.body() ?: throw RuntimeException(response.message())
            postDao.likeById(id)
        }
    }

    override suspend fun save(post: Post) {
        val response = PostsApi.retrofitService.save(post)
        if (!response.isSuccessful) throw RuntimeException("api error")
        response.body() ?: throw RuntimeException(response.message())
        postDao.save(PostEntity.fromDto(post))

    }

    override suspend fun removeById(id: Long) {
        val response = PostsApi.retrofitService.removeById(id)
        if (!response.isSuccessful) throw RuntimeException("api error")
        response.body() ?: throw RuntimeException(response.message())
        postDao.removeById(id)
    }

    override fun data() =
        postDao.getAll().map { it.map(PostEntity::toDto) }

    override suspend fun getAll() {
        val response = PostsApi.retrofitService.getAll()
        if (!response.isSuccessful) throw RuntimeException("api error")
        response.body() ?: throw RuntimeException("body is null")
        postDao.insert(response.body()!!.map { PostEntity.fromDto(it) })
    }
}
