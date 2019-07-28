package wottrich.github.io.yourdiary.firebase

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth

object MyFirebase {

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun createAccount (activity: Activity, email: String, password: String, onSuccess: (Boolean) -> Unit) {

        val createFunction = auth.createUserWithEmailAndPassword(email, password)
        createFunction.addOnCompleteListener(activity) { task ->
            onSuccess(task.isSuccessful)
        }

    }

}