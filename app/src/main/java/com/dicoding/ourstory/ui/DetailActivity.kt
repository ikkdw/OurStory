package com.dicoding.ourstory.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.ourstory.databinding.ActivityDetailBinding
import com.dicoding.ourstory.ui.viewmodel.DetailViewModel
import com.dicoding.ourstory.ui.viewmodel.MainViewModel
import com.dicoding.ourstory.ui.viewmodel.factory.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityDetailBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storyId = intent.getStringExtra(EXTRA_ID) ?: ""
        mainViewModel.getSession().observe(this) { user ->
            val token = user.token
            Log.d("DetailActivity", "Token: $token")
            viewModel.getStoryDetail(storyId, token)
        }

        viewModel.storyState.observe(this) { state ->
            when (state) {
                is DetailViewModel.StoryDetailState.Loading -> {
                    showLoading(true)
                }

                is DetailViewModel.StoryDetailState.Success -> {
                    val story = state.detailResponse
                    Log.d("DetailActivity", "Story: $story")
                    binding.apply {
                        binding.tvName.text = story?.name
                        binding.tvDate.text = story?.createdAt
                        binding.tvDescription.text = story?.description
                        Glide.with(this@DetailActivity).load(story?.photoUrl).into(binding.ivStory)
                        showLoading(false)
                    }
                }

                is DetailViewModel.StoryDetailState.Error -> {
                    showLoading(false)
                    state.exception.toString()
                }
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {

        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TOKEN = "extra_token"
    }
}
