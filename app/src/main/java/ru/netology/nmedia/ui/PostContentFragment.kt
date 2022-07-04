package ru.netology.nmedia.ui


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import ru.netology.nmedia.databinding.PostContentActivityBinding

class PostContentFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )= PostContentActivityBinding.inflate(layoutInflater, container, false).also {binding ->

        val editText = this.arguments?.getString("editContent")

        binding.edit.setText(editText)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding)
        }


    }.root

    private fun onOkButtonClicked(binding: PostContentActivityBinding) {
        val text = binding.edit.text

        if (!text.isNullOrBlank()) {
            val resultBundle = Bundle(1)
            resultBundle.putString(RESULT_KEY, text.toString())
            setFragmentResult(REQUEST_KEY, resultBundle)
        }
        parentFragmentManager.popBackStack()
    }


    companion object{
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "postForSaveContent"
    }


}
