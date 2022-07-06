package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        run {
//            val preferences = getPreferences(Context.MODE_PRIVATE)
//            preferences.edit {
//                putString("key", "value")
//                commit()
//            }
//        }
//
//        run {
//            val preferences = getPreferences(Context.MODE_PRIVATE)
//            val value = preferences.getString("key", "no value")?:return@run
//
//            Snackbar.make(binding.root, value, Snackbar.LENGTH_INDEFINITE).show()
//        }

        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
            viewModel.data.observe(this){posts ->
                adapter.submitList(posts)
        }
        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
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
        viewModel.playVideoLink.observe(this){videoLink->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoLink))
            startActivity(intent)
        }

        val postContentActivityLauncher = registerForActivityResult(
            PostContentActivity.ResultContract()
        ) {result ->
            result?:return@registerForActivityResult
            viewModel.onSaveButtonClicked(result)
        }

        viewModel.navigateToPostContentScreenEvent.observe(this){
            postContentActivityLauncher.launch()
        }

        val editPostContentActivityLauncher = registerForActivityResult(
            PostContentActivity.ResultContractForEdit()
        ) {result ->
            result?:return@registerForActivityResult
            viewModel.onSaveButtonClicked(result)
        }

        viewModel.navigateToEditPostContentScreenEvent.observe(this){
            editPostContentActivityLauncher.launch(it)
        }

    }

}
