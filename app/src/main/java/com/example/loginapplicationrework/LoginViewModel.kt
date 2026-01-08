package com.example.loginapplicationrework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

sealed class LoginState {
    data class Success(val username: String) : LoginState()
    data class Error(val message: String) : LoginState()
    object RegistrationSuccess : LoginState()
}

class LoginViewModel(private val userDao: UserDao) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginState>()
    val loginResult: LiveData<LoginState> = _loginResult

    fun onLoginClicked(username: String, pass: String) {
        if (username.isBlank() || pass.isBlank()) {
            _loginResult.value = LoginState.Error("Please fill in all fields")
            return
        }

        viewModelScope.launch {
            val user = userDao.getUserByUsername(username)

            if (user == null) {
                _loginResult.value = LoginState.Error("User not found")
            } else {
                val hashedInput = HashUtils.sha256(pass.trim())
                if (user.password == hashedInput) {
                    _loginResult.value = LoginState.Success(username)
                } else {
                    _loginResult.value = LoginState.Error("Incorrect password")
                }
            }
        }
    }

    fun onRegisterClicked(username: String, pass: String) {
        if (username.isBlank() || pass.isBlank()) {
            _loginResult.value = LoginState.Error("Fields cannot be empty")
            return
        }

        viewModelScope.launch {
            val existingUser = userDao.getUserByUsername(username)

            if (existingUser != null) {
                _loginResult.value = LoginState.Error("User already exists")
            } else {
                val encryptedPass = HashUtils.sha256(pass)
                userDao.insertUser(User(username, encryptedPass))
                _loginResult.value = LoginState.RegistrationSuccess
            }
        }
    }
}

class LoginViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}