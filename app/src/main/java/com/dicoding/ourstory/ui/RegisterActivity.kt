package com.dicoding.ourstory.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.ourstory.databinding.ActivityRegisterBinding
import com.dicoding.ourstory.ui.custom.EditText
import com.dicoding.ourstory.ui.custom.EditTextEmail
import com.dicoding.ourstory.ui.custom.RegisterButton
import com.dicoding.ourstory.ui.viewmodel.RegisterViewModel
import com.dicoding.ourstory.ui.viewmodel.factory.ViewModelFactory
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerButton: RegisterButton
    private lateinit var editText: EditText
    private lateinit var editTextEmail: EditTextEmail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.insetsController?.hide(WindowInsets.Type.statusBars())
        registerButton = binding.registerButton
        editTextEmail = binding.emailEditText
        editText = binding.passwordEditText

        setMyButtonEnable()
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val name = binding.nameEditText.text.toString()
            val password = editText.text.toString()

            Log.d("Check", email)
            lifecycleScope.launch {
                val response = viewModel.register(name, email, password)
                if (response.message == "User created") {
                    showToast(response.message.toString())
                    val intent= Intent(this@RegisterActivity, WelcomeActivity::class.java)
                    startActivity(intent)
                    Log.d("Success!", "${response.message}")
                } else {
                    showToast(response.message.toString())
                    Log.d("Failed!", "${response.message}")
                }
            }
        }


        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                setMyButtonEnable()
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(100)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 100
        }.start()
    }

    private fun setMyButtonEnable() {
        val password = binding.passwordEditText.text.toString()

        val isPasswordValid = password.length >= 8

        registerButton.isEnabled = isPasswordValid
    }
}