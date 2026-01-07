package com.example.loginapplicationrework

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val usernameInput = findViewById<EditText>(R.id.inputUsername)
        val passwordInput = findViewById<EditText>(R.id.inputPassword)
        val loginBtn = findViewById<Button>(R.id.loginButton)

        viewModel.loginResult.observe(this) { state ->
            when (state) {
                is LoginState.Success -> {
                    val intent = Intent(this, WelcomeActivity::class.java)
                    intent.putExtra("USER_NAME", state.username)
                    startActivity(intent)
                    finish()
                }
                is LoginState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        loginBtn.setOnClickListener {
            viewModel.onLoginClicked(
                usernameInput.text.toString(),
                passwordInput.text.toString()
            )
        }
    }
}