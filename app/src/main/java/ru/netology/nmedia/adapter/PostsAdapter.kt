package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostItemBinding
import ru.netology.nmedia.formatCount


internal class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: PostItemBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when(menuItem.itemId){
                        R.id.remove->{
                            listener.onDeleteClicked(post)
                            true
                        }
                        R.id.edit ->{
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }

                }
            }
        }

        init {
            binding.likeButton.setOnClickListener { listener.onLikeClicked(post) }
            binding.shareButton.setOnClickListener { listener.onShareClicked(post) }
        }

        fun bind(post: Post) {

            this.post = post

            with(binding) {
                authorName.text = post.author
                content.text = post.text
                postDate.text = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm", post.date)
                likeButton.text = formatCount(post.likes.count)
                looksCounter.text = formatCount(post.views)
                shareButton.text = formatCount(post.reposts)
                likeButton.isChecked = post.likes.userLikes
                options.setOnClickListener { popupMenu.show() }
            }
        }

    }


    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }
}
