package com.geek.notekeeper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import io.realm.mongodb.Credentials
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var client: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestServerAuthCode(getString(R.string.server_client_id))
            .build()

        client = GoogleSignIn.getClient(this, googleSignInOptions)

        sign_in_button.setOnClickListener {
            signIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            handleSignInResult(account)
        }
    }

    private fun handleSignInResult(account: GoogleSignInAccount?) {
        try {
            Log.d("LoginActivity", "${account?.serverAuthCode}")
            //1
            val idToken = account?.serverAuthCode

            //signed in successfully, forward credentials to MongoDB realm
            //2
            val googleCredentials = Credentials.google(idToken)
            //3
            noteApp.loginAsync(googleCredentials) {
                if (it.isSuccess) {
                    Log.d("LoginActivity", "Successfully authenticated using Google OAuth")
                    //4
                    startActivity(Intent(this, TaskActivity::class.java))
                } else {
                    Log.d(
                        "LoginActivity",
                        "Failed to Log in to MongoDB Realm: ${it.error.errorMessage}"
                    )
                }
            }
        } catch (exception: ApiException) {
            Log.d("LoginActivity", exception.printStackTrace().toString())
        }
    }

    private fun signIn() {
        val signIntent = client.signInIntent
        startActivityForResult(signIntent, 100)
    }
}