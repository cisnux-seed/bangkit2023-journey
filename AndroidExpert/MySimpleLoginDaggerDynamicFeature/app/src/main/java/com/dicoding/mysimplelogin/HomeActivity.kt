package com.dicoding.mysimplelogin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.core.SessionManager
import com.dicoding.core.UserRepository
import com.dicoding.mysimplelogin.databinding.ActivityHomeBinding
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userRepository = UserRepository(sesi = SessionManager(context = this))

        binding.tvWelcome.text = getString(R.string.welcome, userRepository.getUser())

        binding.btnLogout.setOnClickListener {
            userRepository.logoutUser()
            moveToMainActivity()
        }
        binding.fab.setOnClickListener {
            try {
                installChatModule()
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

    private fun installChatModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleChat = "chat"
        if (splitInstallManager.installedModules.contains(moduleChat)) {
            moveToChatActivity()
            Toast.makeText(this, "open module", Toast.LENGTH_SHORT).show()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleChat)
                .build()

            splitInstallManager.startInstall(request)
                .addOnCompleteListener {
                    Toast.makeText(this, "success installing module", Toast.LENGTH_SHORT)
                        .show()
                    moveToChatActivity()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "error installing module", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }
}
