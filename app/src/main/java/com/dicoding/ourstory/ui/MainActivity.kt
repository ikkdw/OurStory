package com.dicoding.ourstory.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ourstory.data.remote.story.ListStoryItem
import com.dicoding.ourstory.databinding.ActivityMainBinding
import com.dicoding.ourstory.ui.viewmodel.factory.ViewModelFactory
import com.dicoding.ourstory.ui.adapter.StoryAdapter
import com.dicoding.ourstory.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter

    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        setupView()
        setupAction()
        playAnimation()
        setupRecyclerView()
        observeSession()

        binding.uploadButton.setOnClickListener {
            val intent = Intent(this@MainActivity, UploadActivity::class.java)
            intent.putExtra(UploadActivity.EXTRA_TOKEN, token)
            startActivity(intent)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }


    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val name = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
        val logout = ObjectAnimator.ofFloat(binding.logoutButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(name, message, logout)
            startDelay = 100
        }.start()
    }

    private fun getStory(token: String) {
        lifecycleScope.launch {
            viewModel.getAllListStory(token)
        }
    }

    private fun observeSession() {
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                token = user.token
                getStory(token)
            }
        }
    }

    private fun setupRecyclerView() {
        storyAdapter = StoryAdapter(emptyList())
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        binding.rvStory.setHasFixedSize(true)
        binding.rvStory.adapter = storyAdapter

        viewModel.listStory.observe(this) {
            if (it != null) {
                val adapter = StoryAdapter(it.listStory)
                binding.rvStory.adapter = adapter
                adapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: ListStoryItem) {
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_ID, data.id)
                        intent.putExtra(DetailActivity.EXTRA_TOKEN, token)
                        Log.d("id + token", "${data.id} $token")
                        startActivity(intent)
                    }
                })
            }
        }
    }
}