package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.model.FeedModelState
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
    authorAvatar = "",
    isHidden = false
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository =
        PostRepositoryImpl(AppDb.getInstance(application).postDao())

    val data: LiveData<FeedModel> = repository.data
        .map(::FeedModel)
        .asLiveData(Dispatchers.Default)


    private val _state = MutableLiveData(FeedModelState())
    val state: LiveData<FeedModelState>
        get() = _state
    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val toastMessage = SingleLiveEvent<String>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    val newerCount: LiveData<Int> = data.switchMap {
        repository.getNewer(it.posts.firstOrNull()?.id ?: 0L)
            .catch { e -> e.printStackTrace() }
            .asLiveData(Dispatchers.Default)
    }

    init {
        loadPosts()
    }

    fun loadPosts() = viewModelScope.launch {

        try {
            _state.value = FeedModelState(loading = true)
            repository.getAll()
            _state.value = FeedModelState()
        } catch (e: Exception) {
            _state.value = FeedModelState(error = true)
        }


    }

    fun loadVisiblePosts() = viewModelScope.launch {
        try {
            repository.showAll()
        } catch (e: Exception) {
            _state.value = FeedModelState(error = true)
        }

    }

    fun refreshPosts() = viewModelScope.launch {

        try {
            _state.value = FeedModelState(refreshing = true)
            repository.getAll()
            _state.value = FeedModelState()
        } catch (e: Exception) {
            _state.value = FeedModelState(error = true)
        }

    }


    fun save() = viewModelScope.launch {

        try {
            edited.value?.let {
                repository.save(it)
                _postCreated.postValue(Unit)
            }
            edited.value = empty
        } catch (e: Exception) {
            _state.value = FeedModelState(error = true)
        }
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

    fun likeById(id: Long) = viewModelScope.launch {

        val editedPosts = data.value?.posts.orEmpty()
        val isLiked = editedPosts.find { it.id == id }?.likedByMe == true
        editedPosts.filter { it.id == id }.forEach { it.likedByMe = !it.likedByMe }
        try {
            repository.likeById(id, isLiked)
        } catch (e: Exception) {
            _state.value = FeedModelState(error = true)
        }


    }

    fun removeById(id: Long) = viewModelScope.launch {
        try {
            repository.removeById(id)
        } catch (e: Exception) {
            _state.value = FeedModelState(error = true)
        }

    }
}
