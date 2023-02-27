package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Tasks.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentImageBinding
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.LongArg
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class ImageFragment : Fragment() {

    private val viewModel: PostViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentImageBinding.inflate(
        inflater,
        container,
        false
    ).also { binding ->

        viewModel.currentPost.observe(viewLifecycleOwner) {
            val currentPost = it
            if (currentPost != null) {

                if (currentPost.attachment?.url != null) {
                    Glide.with(binding.postImage)
                        .load("http://10.0.2.2:9999/media/${currentPost.attachment?.url}")
                        .placeholder(R.drawable.ic_loading_100dp)
                        .error(R.drawable.ic_error_100dp)
                        .timeout(10_000)
                        .into(binding.postImage)
                    binding.postImage.isVisible = true
                } else binding.postImage.isVisible = false
                if (currentPost.likedByMe) {
                    binding.bottomAppBar.menu?.findItem(R.id.like)
                        ?.setIcon(R.drawable.ic_like_filled_24dp)
                } else {
                    binding.bottomAppBar.menu?.findItem(R.id.like)
                        ?.setIcon(R.drawable.ic_like_outlined_24dp)
                }

                binding.bottomAppBar.setNavigationOnClickListener {
                    viewModel.getPostById(null)
                    findNavController().navigateUp()
                }
                binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.like -> {
                            viewModel.likeById(currentPost.id)
                            true
                        }
                        R.id.share -> {
                            val intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, currentPost.content)
                                type = "text/plain"
                            }

                            val shareIntent =
                                Intent.createChooser(
                                    intent,
                                    getString(R.string.chooser_share_post)
                                )
                            startActivity(shareIntent)
                            true
                        }
                        else -> false
                    }
                }
            }
        }



    }.root


}
