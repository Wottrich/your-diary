package wottrich.github.io.yourdiary.firebase

import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth

object MyFirebase {

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun sendLinkToEmail (email: String, success: (String?) -> Unit) {

        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl("google.com.br")
            .setHandleCodeInApp(true)
            .setAndroidPackageName("wottrich.github.io.yourdiary", true, "21")
            .build()

        auth.sendSignInLinkToEmail(email, actionCodeSettings).addOnCompleteListener {
            if (it.isSuccessful) {
                success("Um email de confirmação foi enviado para $email")
            } else {
                success(null)
            }
        }

    }

    fun createAccount () {

        

    }

}