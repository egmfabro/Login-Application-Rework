package com.example.loginapplicationrework

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val database = UserDatabase.getDatabase(this)
        val factory = LoginViewModelFactory(database.userDao())
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        val userInput = findViewById<EditText>(R.id.inputRegisterUsername)
        val passInput = findViewById<EditText>(R.id.inputRegisterPassword)
        val registerButton = findViewById<Button>(R.id.registerButton)

        viewModel.loginResult.observe(this) { state ->
            when (state) {
                is LoginState.RegistrationSuccess -> {
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is LoginState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        registerButton.setOnClickListener {
            viewModel.onRegisterClicked(userInput.text.toString(), passInput.text.toString())
        }
    }
}