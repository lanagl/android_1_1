package ru.netology.nmedia

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.PostItemBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PostItemBinding.inflate(layoutInflater)
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
        binding.likeButton.setOnClickListener {
            post.likes.userLikes=!post.likes.userLikes
            binding.likeButton.setImageResource(getLikeIconResId(post.likes.userLikes))
        }
    }
    private fun PostItemBinding.render(post: Post){
        authorName.text = post.author
        content.text = post.text
        postDate.text = android.text.format.DateFormat.format("yyyy-MM-dd", post.date)
        likeCounter.text = post.likes.count.toString()
        looksCounter.text=post.views.toString()
        shareCounter.text = post.reposts.toString()
        likeButton.setImageResource(getLikeIconResId(post.likes.userLikes))
    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if(liked) R.drawable.ic_baseline_favorite_24dp else R.drawable.ic_baseline_favorite_border_24dp
}
