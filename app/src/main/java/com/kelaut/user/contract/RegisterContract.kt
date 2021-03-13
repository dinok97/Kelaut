package com.kelaut.user.contract

import com.kelaut.user.model.User

interface RegisterContract {
    interface View{
        fun validateInput(user: User): Boolean
        fun showToastMessage(message: String)
        fun showProgressBar()
        fun hideProgressBar()
        fun navigateToHome()
        fun navigateToLogin()
    }

    interface Presenter {
        fun isValidInput(user: User): Boolean
        fun register(user: User)
        fun saveData(user: User)
        fun onRegisterSuccess()
        fun onRegisterFailure()
    }
}