package com.example.multimodulecrypto.core.data.auth_repository

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.multimodulecrypto.core.data.di.DataModule
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthenticationDataSourceImpl(
    @DataModule.IODispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthenticationDataSource {
    override suspend fun emailAuthenticationSignUp(
        email: String,
        password: String,
        context: Context
    ): Boolean {
        return withContext(ioDispatcher) {
            suspendCoroutine<Boolean> { continuation ->
                val auth = Firebase.auth
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(ContentValues.TAG, "createUserWithEmail:success")
                            continuation.resume(true)
                        } else {
                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                context,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            continuation.resume(false)
                        }
                    }
            }
        }
    }

    override suspend fun emailAuthenticationSignIn(
        email: String,
        password: String,
        context: Context
    ): Boolean {
        return withContext(ioDispatcher) {
            suspendCoroutine<Boolean> { continuation ->
                val auth = Firebase.auth
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(ContentValues.TAG, "signInWithEmail:success")
                            continuation.resume(true)
                        } else {
                            Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                context,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            continuation.resume(false)
                        }
                    }
            }
        }
    }
}