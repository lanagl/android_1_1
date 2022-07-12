package ru.netology.nmedia.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.PostRepositoryImpl
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.util.SingleLiveEvent
import java.util.*

class PostViewModel(
    application: Application
) : AndroidViewModel(application),
    PostInteractionListener {

    private val repository: PostRepository = PostRepositoryImpl(
        dao = AppDb.getInstance(
            context = application
        ).postDao
    )

    val data by repository::data

    val currentPost = MutableLiveData<Post?>(null)
    val navigateToPostContentScreenEvent = SingleLiveEvent<Unit?>()
    val navigateToEditPostContentScreenEvent = SingleLiveEvent<String?>()
    val sharePostContent = SingleLiveEvent<String>()
    val playVideoLink = SingleLiveEvent<String?>()

    fun onSaveButtonClicked(content: String) {
        if(content.isBlank()) return
        val rightNow = Calendar.getInstance().time
        val newPost = currentPost.value?.copy(text = content)?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Author",
            text = content,
            date = rightNow.time,
            likesCount = 0,
            likeByMe = false,
            reposts = 0,
            views = 0,
            video = "https://youtu.be/Ed0Xdi_xdfw"

        )
        repository.save(newPost)

        currentPost.value = null
    }


    //region PostInteractionListener

    override fun onLikeClicked(post: Post) = repository.like(post.id)
    override fun onShareClicked(post: Post) {
        sharePostContent.value = post.text
        repository.share(post.id)
    }
    override fun onDeleteClicked(post: Post) = repository.delete(post.id)
    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToEditPostContentScreenEvent.value = post.text
    }

    fun onAddClicked() {
        currentPost.value = null
        navigateToPostContentScreenEvent.call()
    }

    override fun onPlayClicked(post: Post) {
        if (post.video!=null) {
            playVideoLink.value = post.video
        }
    }


    //endregion PostInteractionListener
}
