package ru.netology.nmedia.data.impl

import androidx.lifecycle.map
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel

class PostRepositoryImpl(
    private val dao: PostDao
): PostRepository {

    override val data = dao.getAll().map { entities->
        entities.map { it.toModel() }
    }

    override fun save(post: Post) {
        val id = post.id

        if(id ==0L) dao.insert(post.toEntity()) else dao.updateContentById(post.id, post.text)

    }

    override fun like(id: Long) {
        dao.likeById(id)
    }

    override fun delete(id: Long) {
        dao.removeById(id)
    }

    override fun share(id: Long) {
        dao.shareById(id)
    }

}
