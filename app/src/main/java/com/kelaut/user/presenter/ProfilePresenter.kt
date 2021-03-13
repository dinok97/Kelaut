package com.kelaut.user.presenter

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kelaut.user.contract.ProfileContract
import com.kelaut.user.model.User
import com.kelaut.user.utils.Constant.Collection.Companion.USER

class ProfilePresenter (_view: ProfileContract.View): ProfileContract.Presenter {

    private val view: ProfileContract.View = _view

    init {
        loadUserData()
    }

    override fun loadUserData() {
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val mUser = FirebaseAuth.getInstance().currentUser
        val userId = mUser?.uid

//        view.showProgressBar()
        firebaseFirestore.collection(USER).document(userId ?: "user").get()
            .addOnSuccessListener {document ->
                if(document!=null){
                    val user: User? = document.toObject(User::class.java)
                    fillUserDataToLayout(user)
                } else {
                    //show toast message
                    Log.d("USER-DATA", "fail to catch user data")
                }
            }.addOnFailureListener {
                //show toast message
                Log.d("USER-DATA", "failure catch data from firebase")
            }
    }

    override fun fillUserDataToLayout(user: User?) {
        view.fillDataToLayout(user)
    }

    override fun logout() {
        FirebaseAuth.getInstance().signOut()
        view.navigateToLogin()
    }

}