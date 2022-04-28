package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Likes
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.formatCount
import java.util.*

class InMemoryPostRepository: PostRepository {

    private var post = Post(
        id = 0L,
        author = "Автор",
        text = "Текст поста",
        date =  Date(),
        likes = Likes(count = 0, userLikes = false),
        reposts = 0,
        views = 0
    )
    override val data =MutableLiveData(post)

    override fun like() {
        val currentPost = checkNotNull(data.value){
            "Data value should not be null"
        }
        val currentLikes = checkNotNull(currentPost.likes){
            "Data value should not be null"
        }
        val userLikes = !currentLikes.userLikes

        val count: Int = if(userLikes) {
            currentLikes.count+1
        } else {
            currentLikes.count-1
        }
        val likes = currentLikes.copy(userLikes = userLikes, count=count)
        val likedPost = currentPost.copy(likes = likes)
        data.value = likedPost
    }

    override fun share() {
        val currentPost = checkNotNull(data.value){
            "Data value should not be null"
        }
        val sharedPost = currentPost.copy(reposts = currentPost.reposts+1)
        data.value = sharedPost
    }
}
