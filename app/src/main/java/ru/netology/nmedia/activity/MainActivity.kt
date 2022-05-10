package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
            viewModel.data.observe(this){posts ->
                adapter.submitList(posts)
        }
        binding.savePost.setOnClickListener {
            with(binding.contentEdit){
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
            }

        }
        binding.cancelEdit.setOnClickListener {
            viewModel.currentPost.value = null
        }

        viewModel.currentPost.observe(this){currentPost ->
            val context = currentPost?.text
            with(binding.contentEdit){
                setText(context)
                binding.contentOld.setText(context)
                if(context!=null) {
                    binding.oldPost.visibility = View.VISIBLE
                    requestFocus()
                    showKeyboard()
                }
                else
                {
                    binding.oldPost.visibility = View.GONE
                    clearFocus()
                    hideKeyboard()
                }
            }
        }

        viewModel.sharePostContent.observe(this){postContent->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }
    }

}
