package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()
        viewModel.data.observe(this){post ->
            with(binding) {
                binding.render(post)
            }
        }


        binding.post.likeButton.setOnClickListener {
            viewModel.like()
        }

        binding.post.shareButton.setOnClickListener {
            viewModel.share()
        }


    }
    private fun ActivityMainBinding.render(postItem: Post){
        post.authorName.text = postItem.author
        post.content.text = postItem.text
        post.postDate.text = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm", postItem.date)
        post.likeCounter.text = formatCount(postItem.likes.count)
        post.looksCounter.text=formatCount(postItem.views)
        post.shareCounter.text = formatCount(postItem.reposts)
        post.likeButton.setImageResource(getLikeIconResId(postItem.likes.userLikes))
    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if(liked) R.drawable.ic_baseline_favorite_24dp else R.drawable.ic_baseline_favorite_border_24dp
}
