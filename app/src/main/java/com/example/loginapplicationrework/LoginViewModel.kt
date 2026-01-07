package com.example.loginapplicationrework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class LoginState {
    data class Success(val username: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<LoginState>()
    val loginResult: LiveData<LoginState> = _loginResult

    fun onLoginClicked(username: String, pass: String) {
        if (username.isBlank() || pass.isBlank()) {
            _loginResult.value = LoginState.Error("Please fill in all fields")
            return
        }

        viewModelScope.launch {
            delay(1000)

            if (username == "Admin" && pass == "1234") {
                _loginResult.value = LoginState.Success(username)
            } else {
                _loginResult.value = LoginState.Error("Invalid Username or Password")
            }
        }
    }
}