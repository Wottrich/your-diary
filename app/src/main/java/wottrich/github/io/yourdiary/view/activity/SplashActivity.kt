package wottrich.github.io.yourdiary.view.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.activity_splash.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.generics.BaseActivity
import wottrich.github.io.yourdiary.model.User
import wottrich.github.io.yourdiary.view.activity.firstAccess.UserRegisterActivity
import wottrich.github.io.yourdiary.view.activity.profile.ProfileActivity

class SplashActivity : BaseActivity(R.layout.activity_splash) {

    private val RETURN_FINGERPRINT = 300
    private var hasLockScreen = false

    override fun initValues() {
//        if (gAuth.currentUser != null) {
//            gAuth.currentUser!!.sendEmailVerification().addOnCompleteListener {
//                if (it.isSuccessful) {
//                    gAuth.currentUser?.let { currentUser ->
//                        getUserAuth(currentUser.uid) { user, message ->
//                            if (user != null) {
//
//                            } else {
//                                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    }
//                } else {
//                    gAuth.currentUser!!.delete()
//                    startMyActivity(SingInActivity::class) { intent ->
//                        intent.putExtra("login", true)
//                        intent
//                    }
//                }
//            }
//        } else {
//        }
            if (box<User>().isEmpty) {
                startActivity(Intent(this, UserRegisterActivity::class.java))
                return
            } else {
                val user = boxList<User>().first()
                hasLockScreen = user.lockApp

                if (user.lockApp) startActivityForResult(intentLockActivity(), RETURN_FINGERPRINT)
                else {
                    startMyActivity(ProfileActivity::class)
                    finish()
                }

            }


        btnRetry.visibility = View.GONE

        btnRetry.setOnClickListener {
            startActivityForResult(intentLockActivity(), RETURN_FINGERPRINT)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        when (requestCode) {
            RETURN_FINGERPRINT -> {
                if (resultCode == Activity.RESULT_OK) {
                    startMyActivity(ProfileActivity::class)
                    finish()
                } else {
                    btnRetry.visibility = View.VISIBLE
                }
            }
        }


        super.onActivityResult(requestCode, resultCode, data)
    }

}
