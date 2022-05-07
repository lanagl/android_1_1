package ru.netology.nmedia

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 0L,
            author = "Автор",
            text = "Текст поста",
            date =  Date(),
            likes = Likes(count = 0, userLikes = false),
            reposts = 0,
            views = 0
        )
        binding.render(post)
        binding.post.likeButton.setOnClickListener {
            post.likes.userLikes=!post.likes.userLikes
            binding.post.likeButton.setImageResource(getLikeIconResId(post.likes.userLikes))
            if(post.likes.userLikes) {
                post.likes.count++
            } else {
                post.likes.count--
            }
            binding.post.likeCounter.text = formatCount(post.likes.count)
        }

        binding.post.shareButton.setOnClickListener {
            post.reposts++
            binding.post.shareCounter.text = formatCount(post.reposts)
        }


    }
    private fun ActivityMainBinding.render(postItem: Post){
        post.authorName.text = postItem.author

        post.content.text = postItem.text
        post.postDate.text = android.text.format.DateFormat.format("yyyy-MM-dd", postItem.date)
        post.likeCounter.text = formatCount(postItem.likes.count)
        post.looksCounter.text=formatCount(postItem.views)
        post.shareCounter.text = formatCount(postItem.reposts)
        post.likeButton.setImageResource(getLikeIconResId(postItem.likes.userLikes))
    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if(liked) R.drawable.ic_baseline_favorite_24dp else R.drawable.ic_baseline_favorite_border_24dp
}
