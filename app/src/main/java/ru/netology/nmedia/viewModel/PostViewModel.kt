package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel: ViewModel() {
    private val repository: PostRepository = InMemoryPostRepository()
    val data by repository::data
    fun like(post: Post) = repository.like(post.id)
    fun share(post: Post) = repository.share(post.id)
}
