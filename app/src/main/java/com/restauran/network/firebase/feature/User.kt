package com.restauran.network.firebase.feature

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import com.restauran.R
import com.restauran.network.RequestListener
import com.restauran.network.StringRequestListener
import com.restauran.network.firebase.model.UserData
import com.restauran.network.firebase.utils.FirebaseReferances.Companion.getAuthCurrentUserReference
import com.restauran.network.firebase.utils.FirebaseReferances.Companion.getAuthReference
import com.restauran.utils.RestaurantsApp
import java.util.*

class User {
    companion object {
        private val TAG: String = User::class.java.simpleName
        private val getUserDataListener: StringRequestListener<UserData>? = null
        private var instance: User? = null
        fun getInstance(): User? {
            if (instance == null) instance = User()
            return instance
        }
    }

    fun userSignUp(
        userData: UserData,
        password: String,
        requestListener: RequestListener<UserData?>
    ) {
        getAuthReference().createUserWithEmailAndPassword(userData.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser? = getAuthCurrentUserReference()
                    requestListener.onSuccess(userData)
                } else {
                    getInstance()?.taskVoidException2(task)?.let { requestListener.onFail(it) }
                }
            }
    }

    fun loginWithEmailAndPassword(
        email: String,
        password: String,
        requestListener: StringRequestListener<String?>
    ) {
        getAuthReference().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = getAuthReference().currentUser?.uid
                    requestListener.onSuccess(
                        message = RestaurantsApp.getInstance()
                            ?.getString(R.string.login_successful).toString(),
                        data = email
                    )
                } else {
                    getInstance()?.taskVoidException2(task)?.let { requestListener.onFail(it) }
                }
            }
    }

    private fun taskVoidException2(task: Task<AuthResult>): String? {
        return try {
            throw Objects.requireNonNull(task.exception)!!
        } catch (e: FirebaseAuthWeakPasswordException) {
            RestaurantsApp.getInstance()?.getString(R.string.error_week_password) ?: ""
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            RestaurantsApp.getInstance()?.getString(R.string.pass_email_invalid) ?: ""
        } catch (e: FirebaseAuthInvalidUserException) {
            RestaurantsApp.getInstance()?.getString(R.string.pass_email_invalid) ?: ""
        } catch (e: FirebaseNoSignedInUserException) {
            RestaurantsApp.getInstance()?.getString(R.string.emailInvalid) ?: ""
        } catch (e: FirebaseAuthEmailException) {
            RestaurantsApp.getInstance()?.getString(R.string.emailInvalid) ?: ""
        } catch (e: FirebaseAuthUserCollisionException) {
            RestaurantsApp.getInstance()?.getString(R.string.ERR_emailExist) ?: ""
        } catch (e: Exception) {
            RestaurantsApp.getInstance()?.getString(R.string.error) ?: ""
        }
    }
}
