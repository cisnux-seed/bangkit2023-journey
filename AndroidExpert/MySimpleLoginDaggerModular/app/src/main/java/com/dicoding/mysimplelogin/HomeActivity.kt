package com.dicoding.mysimplelogin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.core.UserRepository
import com.dicoding.mysimplelogin.databinding.ActivityHomeBinding
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvWelcome.text = getString(R.string.welcome, userRepository.getUser())

        binding.btnLogout.setOnClickListener {
            userRepository.logoutUser()
            moveToMainActivity()
        }
        binding.fab.setOnClickListener {
            try {
                moveToChatActivity()
            } catch (e: Exception) {
                Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun moveToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun moveToChatActivity() {
        startActivity(Intent(this, Class.forName("com.dicoding.mysamplelogin.chat.ChatActivity")))
    }
}
