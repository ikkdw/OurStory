package com.dicoding.ourstory.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ourstory.databinding.ActivityMainBinding
import com.dicoding.ourstory.ui.adapter.StoryAdapter
import com.dicoding.ourstory.ui.viewmodel.MainViewModel
import com.dicoding.ourstory.ui.viewmodel.factory.ViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
        observeSession()

        binding.uploadButton.setOnClickListener {
            val intent = Intent(this@MainActivity, UploadActivity::class.java)
            intent.putExtra(UploadActivity.EXTRA_TOKEN, token)
            startActivity(intent)
        }



        binding.mapsButton.setOnClickListener {
            val intent = Intent(this@MainActivity, MapsActivity::class.java)
            startActivity(intent)
        }
    }

//    override fun onResume(){
//        super.onResume()
//        val token = "Bearer $token"
//        getStory(token)
//    }

    private fun setupView() {
        window.insetsController?.hide(WindowInsets.Type.statusBars())
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
            viewModel.getStory(token)
        }
    }

    private fun observeSession() {
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                token = user.token
                val token2 = "Bearer $token"
                getData(token2)
            }
        }
    }

    private fun getData(token: String) {
//        lifecycleScope.launch {
//            viewModel.getStory(token)
//            Log.d("MainActivityToken", "token : $token")
//        }

        val adapter = StoryAdapter()
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        binding.rvStory.adapter = adapter
        viewModel.getStory(token).observe(this){
            adapter.submitData(lifecycle, it)
            Log.d("MainActivityToken", "$it")
        }
    }
}