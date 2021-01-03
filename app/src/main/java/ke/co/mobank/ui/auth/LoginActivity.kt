package ke.co.mobank.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ke.co.mobank.databinding.ActivityLoginBinding
import ke.co.mobank.ui.MainActivity

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    private lateinit var binding: ActivityLoginBinding
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWidgets()
    }

    private fun setupWidgets() {
        binding.signUpTextView.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailField.text.toString()
            val password = binding.passwordField.text.toString()

            if (email.isBlank()) {
                binding.emailField.error = "Email is required"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailField.error = "Valid email address is required"
                return@setOnClickListener
            }

            if (password.isBlank() || password.length < 6) {
                binding.passwordField.error = "At least a 6 chars password required"
                return@setOnClickListener
            }

            login(email, password)
        }
    }

    private fun login(email: String, password: String) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { loginTask ->
                if (loginTask.isSuccessful) sendToMain()
                else {
                    Log.e(TAG, "login: Login Error", loginTask.exception)
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) sendToMain()

    }

    private fun sendToMain() {
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }.also { startActivity(it) }
    }
}