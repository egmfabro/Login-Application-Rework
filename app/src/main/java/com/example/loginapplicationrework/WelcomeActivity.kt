package com.example.loginapplicationrework

import android.os.Bundle
import android.widget.TextView
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = intent.getStringExtra("USER_NAME") ?: "User"

        val textView = TextView(this).apply {
            text = getString(R.string.welcomeUser, username)
            textSize = 30f
            gravity = Gravity.CENTER
        }

        setContentView(textView)
    }
}