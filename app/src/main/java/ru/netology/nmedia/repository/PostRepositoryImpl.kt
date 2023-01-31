package ru.netology.nmedia.repository

import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post


class PostRepositoryImpl : PostRepository {

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9999"
        private val jsonType = "application/json".toMediaType()
    }

    override fun getById(id: Long, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.getById(id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }

                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException(t.localizedMessage))
            }

        })

    }


    override fun likeById(id: Long, isLiked: Boolean, callback: PostRepository.Callback<Post>) {
        if (isLiked) {
            PostsApi.retrofitService.dislikeById(id).enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (!response.isSuccessful) {
                        callback.onError(RuntimeException(response.message()))
                        return
                    }

                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(RuntimeException(t.localizedMessage))
                }

            })
        } else {
            PostsApi.retrofitService.likeById(id).enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (!response.isSuccessful) {
                        callback.onError(RuntimeException(response.message()))
                        return
                    }

                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(RuntimeException(t.localizedMessage))
                }

            })
        }
    }

    override fun save(post: Post, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.save(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }

                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException(t.localizedMessage))
            }

        })
    }

    override fun removeById(id: Long, callback: PostRepository.Callback<Unit>) {

        PostsApi.retrofitService.removeById(id).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }

                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                callback.onError(RuntimeException(t.localizedMessage))
            }

        })
    }

    override fun getAll(callback: PostRepository.Callback<List<Post>>) {
        PostsApi.retrofitService.getAll().enqueue(object : Callback<List<Post>> {
            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }

                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                callback.onError(RuntimeException(t.localizedMessage))
            }

        })
    }
}
