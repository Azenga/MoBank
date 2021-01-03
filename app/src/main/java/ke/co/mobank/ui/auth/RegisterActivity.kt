package ke.co.mobank.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ke.co.mobank.databinding.ActivityRegisterBinding
import ke.co.mobank.ui.MainActivity

class RegisterActivity : AppCompatActivity() {

    private val TAG = "RegisterActivity"

    private lateinit var binding: ActivityRegisterBinding

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWidgets()
    }

    private fun setupWidgets() {

        binding.signInTextView.setOnClickListener { finish() }

        binding.registerButton.setOnClickListener {
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

            register(email, password)
        }
    }

    private fun register(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { loginTask ->
                if (loginTask.isSuccessful) sendToMain()
                else {
                    Log.e(TAG, "login: Login Error", loginTask.exception)
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun sendToMain() {
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }.also { startActivity(it) }
    }
}