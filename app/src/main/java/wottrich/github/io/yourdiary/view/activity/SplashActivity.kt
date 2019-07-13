package wottrich.github.io.yourdiary.view.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.activity_splash.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.box
import wottrich.github.io.yourdiary.extensions.boxList
import wottrich.github.io.yourdiary.extensions.intentLockActivity
import wottrich.github.io.yourdiary.extensions.startMyActivity
import wottrich.github.io.yourdiary.generics.BaseActivity
import wottrich.github.io.yourdiary.model.User
import wottrich.github.io.yourdiary.view.activity.firstAccess.UserRegisterActivity

class SplashActivity : BaseActivity(R.layout.activity_splash) {

    private val RETURN_FINGERPRINT = 300
    private var hasLockScreen = false

    override fun initValues() {
        if (box<User>().isEmpty) {
            startActivity(Intent(this, UserRegisterActivity::class.java))
            return
        } else {
            val user = boxList<User>().first()
            hasLockScreen = user.lockApp
            if (user.lockApp) {
                startActivityForResult(intentLockActivity(), RETURN_FINGERPRINT)
            } else {
                startMyActivity(MainActivity::class.java)
            }
        }

        if (hasLockScreen) {
            btnRetry.visibility = View.VISIBLE
        } else {
            btnRetry.visibility = View.GONE
        }

        btnRetry.setOnClickListener {
            startActivityForResult(intentLockActivity(), RETURN_FINGERPRINT)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RETURN_FINGERPRINT -> {
                    startMyActivity(MainActivity::class.java)
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

}
