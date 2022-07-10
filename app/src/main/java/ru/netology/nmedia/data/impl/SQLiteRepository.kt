package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao

class SQLiteRepository(
    private val dao: PostDao
): PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }
    override val data = MutableLiveData(dao.getAll())

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        data.value = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
        data.value = posts
    }

    override fun like(id: Long) {
        dao.likeById(id)
        data.value = posts.map {
            if (it.id != id) it else it.copy(
                likeByMe = !it.likeByMe,
                likesCount = if (it.likeByMe) it.likesCount - 1 else it.likesCount + 1
            )
        }
    }

    override fun delete(id: Long) {
        dao.removeById(id)
        data.value = posts.filter { it.id != id }
    }

    override fun share(postId: Long) {
        data.value = posts.map { if (it.id != postId) it else it.copy(reposts = it.reposts + 1) }
    }

}
