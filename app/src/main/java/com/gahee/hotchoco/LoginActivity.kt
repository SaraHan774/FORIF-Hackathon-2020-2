package com.gahee.hotchoco

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.gahee.hotchoco.Constants.Companion.USER_NAME_KEY
import com.gahee.hotchoco.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_login
        )

        val prefs = getSharedPreferences("appPref", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(
            "isFirstOpen", true
        )
        editor.apply()

        setUpMarshMallowAnimations(binding)

        binding.loginBtn.setOnClickListener {
            signIn()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, "Login with google failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        Toast.makeText(this, "HELLO ${user?.displayName}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@LoginActivity, InfoActivity::class.java)
        intent.putExtra(USER_NAME_KEY, user?.displayName)
        startActivity(intent)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun setUpMarshMallowAnimations(binding: ActivityLoginBinding){
        val image = binding.marshmallow
        val image1 = binding.marshmallow1
        val image2 = binding.marshmallow2
        val image3 = binding.marshmallow3
        val image4 = binding.marshmallow4
        val image5 = binding.marshmallow5
        val image6 = binding.marshmallow6
        val image7 = binding.marshmallow7

        dropAndRotate(image)
        dropAndRotate1(image1)
        dropAndRotate2(image2)
        dropAndRotate(image3)
        dropAndRotate1(image4)
        dropAndRotate2(image5)
        dropAndRotate(image6)
        dropAndRotate1(image7)
    }

    private fun dropAndRotate(view: ImageView){
        ObjectAnimator.ofFloat(
            view, "translationY", 1500f
        ).apply {
            duration = 4000
            repeatCount = Animation.INFINITE
            start()
        }
        val kf0 = Keyframe.ofFloat(0f, 0f)
        val kf1 = Keyframe.ofFloat(.5f, 360f)
        val kf2 = Keyframe.ofFloat(1f, 0f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2)
        ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            duration = 4000
            repeatCount = Animation.INFINITE
            start()
        }
    }

    private fun dropAndRotate1(view: ImageView){
        ObjectAnimator.ofFloat(
            view, "translationY", 1500f
        ).apply {
            duration = 4000
            repeatCount = Animation.INFINITE
            start()
        }
        val kf0 = Keyframe.ofFloat(0f, 70f)
        val kf1 = Keyframe.ofFloat(.3f, 270f)
        val kf3 = Keyframe.ofFloat(.7f, 180f)
        val kf2 = Keyframe.ofFloat(1f, 360f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf3, kf2)
        ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            duration = 4000
            repeatCount = Animation.INFINITE
            start()
        }
    }

    private fun dropAndRotate2(view: ImageView){
        ObjectAnimator.ofFloat(
            view, "translationY", 1500f
        ).apply {
            duration = 4000
            repeatCount = Animation.INFINITE
            start()
        }
        val kf0 = Keyframe.ofFloat(0f, 80f)
        val kf1 = Keyframe.ofFloat(.3f, 120f)
        val kf3 = Keyframe.ofFloat(.7f, 180f)
        val kf2 = Keyframe.ofFloat(1f, 30f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf3, kf2)
        ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            duration = 4000
            repeatCount = Animation.INFINITE
            start()
        }
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}