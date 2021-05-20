package id.junkman.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.junkman.BuildConfig
import id.junkman.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLoginBinding
  private lateinit var googleSignInClient: GoogleSignInClient
  private lateinit var auth: FirebaseAuth
  private lateinit var store: FirebaseFirestore

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Initialize Firebase
    auth = Firebase.auth
    store = Firebase.firestore

    // Configure Google Sign In
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(BuildConfig.OAUTH2_WEB_CLIENT_ID)
      .requestEmail()
      .build()

    googleSignInClient = GoogleSignIn.getClient(this, gso)

    binding.cardViewActionLogin.setOnClickListener { signIn() }
  }

  override fun onStart() {
    super.onStart()
    // Check if user is signed in (non-null) and update UI accordingly.
    val currentUser = auth.currentUser
    currentUser?.let {
      redirectToLanding(it)
    }
  }

  private fun signIn() {
    val signInIntent = googleSignInClient.signInIntent
    startActivityForResult(signInIntent, RC_SIGN_IN)
  }

  private fun firebaseAuthWithGoogle(idToken: String) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
      .addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
          val currentUser = auth.currentUser
          val isNew = task.result?.additionalUserInfo?.isNewUser
          // Sign in success, update UI with the signed-in user's information
          currentUser?.let { user ->
            if (isNew != null && isNew == true) {
              storeUserToFirestore(user)
            }
            redirectToLanding(user)
          }
        } else {
          // If sign in fails, display a message to the user.
          Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
      }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    if (requestCode == RC_SIGN_IN) {
      val task = GoogleSignIn.getSignedInAccountFromIntent(data)
      try {
        // Google Sign In was successful, authenticate with Firebase
        val account = task.getResult(ApiException::class.java)!!
        firebaseAuthWithGoogle(account.idToken!!)
      } catch (e: ApiException) {
        // Google Sign In failed, update UI appropriately
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun redirectToLanding(user: FirebaseUser) {
    Toast.makeText(this, user.displayName, Toast.LENGTH_SHORT).show()
    startActivity(Intent(this, LandingActivity::class.java))
    finish()
  }

  private fun storeUserToFirestore(user: FirebaseUser) {
    val documentReference = store.collection("User").document(user.uid)
    val userInfo = hashMapOf(
      "Name" to user.displayName,
      "Email" to user.email,
      "Phone" to user.phoneNumber,
      "Photo" to user.photoUrl,
      "Address" to "",
      "Role" to "Customer"
    )
    documentReference.set(userInfo)
  }

  companion object {
    private const val RC_SIGN_IN = 99
  }
}