package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    likes = 0,
    published = "",
    authorAvatar = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }

    fun loadPosts() {

        _data.postValue(FeedModel(loading = true))

        repository.getAll(object : PostRepository.GetAllCallback {
            override fun onSuccess(posts: List<Post>) {
                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })

    }

    fun save() {
        edited.value?.let {

            repository.save(it, object : PostRepository.SaveRemoveCallback {
                override fun onSuccess() {
                    _postCreated.postValue(Unit)
                }

            })
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) {

        val old = _data.value?.posts.orEmpty()
        val editedPosts = _data.value?.posts.orEmpty()
        val isLiked = editedPosts.find { it.id == id }?.likedByMe == true
        editedPosts.filter { it.id == id }.forEach { it.likedByMe = !it.likedByMe }


        _data.postValue(_data.value?.copy(posts = editedPosts))

        repository.likeById(id, isLiked, object : PostRepository.PostCallback {
            override fun onSuccess(post: Post) {
                val posts = _data.value?.posts.orEmpty().map { if (it.id == id) post else it }
                _data.postValue(_data.value?.copy(posts = posts))
            }

            override fun onError(e: Exception) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        })

    }

    fun removeById(id: Long) {

        val old = _data.value?.posts.orEmpty()
        _data.postValue(
            _data.value?.copy(posts = _data.value?.posts.orEmpty()
                .filter { it.id != id }
            )
        )
        repository.removeById(id, object : PostRepository.SaveRemoveCallback {
            override fun onError(e: Exception) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        })


    }
}
