package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.*
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class FeedFragment : Fragment() {
    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        setFragmentResultListener(requestKey = PostContentFragment.REQUEST_KEY){
            requestKey, bundle ->
                if (requestKey!= PostContentFragment.REQUEST_KEY) return@setFragmentResultListener
                val newPostContent = bundle.getString(PostContentFragment.RESULT_KEY)?:return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newPostContent)
        }

        viewModel.navigateToPostContentScreenEvent.observe(this){
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, PostContentFragment())
                addToBackStack(null)
            }
        }

        viewModel.navigateToEditPostContentScreenEvent.observe(this){

            parentFragmentManager.commit {
                val fragment = PostContentFragment().apply {
                    arguments = Bundle().apply {
                        putString("editContent", it)
                    }
                }
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(null)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )= ActivityMainBinding.inflate(layoutInflater, container, false).also {binding ->
        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner){posts ->
            adapter.submitList(posts)
        }
        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }

    }.root

}
