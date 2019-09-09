package wottrich.github.io.yourdiary.firebase

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import wottrich.github.io.yourdiary.extensions.getUser

fun Activity.createAccount(
    email: String, password: String,
    onCreatedAccount: (Boolean) -> Unit,
    onSavedAccount: (Boolean) -> Unit
) {

    gAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
        onCreatedAccount(task.isSuccessful)

        if (task.isSuccessful) {
            gAuth.currentUser?.let {

                val user = getUser()
                user.uid = it.uid
                user.email = it.email
                UserAction().toSave(user) { success ->
                    onSavedAccount(success)
                }


            }
        } else {
            onSavedAccount(false)
        }

    }

}

fun Activity.singInAccount (
    email: String, password: String,
    onSingInAccount: (Boolean) -> Unit
) {
    gAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
        onSingInAccount(task.isSuccessful)
    }
}