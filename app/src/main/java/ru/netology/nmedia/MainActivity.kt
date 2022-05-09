package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
            viewModel.oldPost.value = null
            viewModel.currentPost.value = null
        }


        viewModel.oldPost.observe(this){oldPost->
            with(binding.contentOld){
                val context = oldPost?.text
                setText(context)
                if(context!=null) {
                    binding.oldPost.visibility = View.VISIBLE
                }
                else
                {
                    binding.oldPost.visibility = View.GONE
                }
            }

        }


        viewModel.currentPost.observe(this){currentPost ->
            with(binding.contentEdit){
                val context = currentPost?.text
                setText(context)
                if(context!=null) {
                    requestFocus()
                    showKeyboard()
                }
                else
                {
                    clearFocus()
                    hideKeyboard()
                }
            }
        }
    }

}
